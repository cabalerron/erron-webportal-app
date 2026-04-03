/*
 * LGN001Service
 * Service for managing user authentication
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 * 
 */

 package ph.com.project01.webportal.service;

 import ph.com.project01.webportal.model.UsersMaster;
 import ph.com.project01.webportal.repository.LGN001Repository;
 import java.time.LocalDateTime;
 import java.util.HashSet;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.stereotype.Service;
 
 @Service
 public class LGN001Service implements UserDetailsService {
 
     @Autowired
     private LGN001Repository userMasterRepository;
 
     private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 
     public UsersMaster findByAccountid(String accountId) {
         return userMasterRepository.findByAccountId(accountId);
     }
 
     @Override
     public UserDetails loadUserByUsername
     (String accountId) throws UsernameNotFoundException 
     {
         UsersMaster user = userMasterRepository
         .findByAccountId(accountId);
         if (user == null) {
             throw new UsernameNotFoundException("User not found");
         }
 
         Set<GrantedAuthority> authorities = new HashSet<>();
         authorities.add(new SimpleGrantedAuthority(user
             .getRoleId() == 1 ? "1" : "2"));
 
         return new org.springframework.security.core.userdetails.User(
                 user.getAccountId(), 
                 user.getPassword(), 
                 authorities);
     }
 
     public boolean authenticate(String accountId, String rawPassword) {
         UsersMaster user = userMasterRepository
             .findByAccountId(accountId);
         if (user == null || 
             user.getInvalidTry() >= 5 || 
             user.getLockFlag() == 1) {
             return false;
         }
         boolean passwordMatch = passwordEncoder
             .matches(rawPassword, user.getPassword());
         if (passwordMatch) {
             userMasterRepository
                 .updateInvalidTryAndLockFlag(
                     accountId, 
                     0, 
                     0);
 
             userMasterRepository
                 .updateLastLoginDate(
                     accountId,
                     LocalDateTime.now());
             return true;
         } else {
             int attempts = user.getInvalidTry() + 1;
             if (attempts >= 5) {
                 userMasterRepository
                     .updateInvalidTryAndLockFlag(
                         accountId, 
                         attempts, 
                         1);
             } else {
                 userMasterRepository
                     .updateInvalidTry(
                         accountId, 
                         attempts);
             }
             return false;
         }
     }
 
     public void resetLoginAttempts(String accountId) {
         UsersMaster user = userMasterRepository
             .findByAccountId(accountId);
         if (user != null) {
             user.setInvalidTry(0);
             user.setLockFlag(0);
             userMasterRepository.save(user); 
         }
     }
 }
 