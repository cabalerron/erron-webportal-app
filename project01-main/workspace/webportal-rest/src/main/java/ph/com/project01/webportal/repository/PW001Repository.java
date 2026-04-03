/*
 * PW001Repository
 * Repository for UsersMaster email and token 
 *
 * v 00.001 - 10/22/2024
 *
 * PIC: emonteverde
 */

 package ph.com.project01.webportal.repository;

 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Modifying;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.stereotype.Repository;
 import ph.com.project01.webportal.model.UsersMaster;
 import jakarta.transaction.Transactional;
 
 @Repository
 public interface PW001Repository extends JpaRepository<UsersMaster, Integer> {
 
     UsersMaster findByMailAddress(String mailAddress);
     
     UsersMaster findByResetToken(String resetToken);
 
     @Query("SELECT CASE WHEN u.tokenExp > CURRENT_TIMESTAMP " +
            "THEN true ELSE false END FROM UsersMaster u " +
            "WHERE u.resetToken = :token")
     boolean isTokenValid(String token);
 
     @Modifying
     @Transactional
     @Query("UPDATE UsersMaster u SET u.password = :hashedPassword " +
            "WHERE u.resetToken = :token")
     void updatePasswordByToken(String token, String hashedPassword);
 }
 
