/*
 * LGN001Repository
 * Repository for UsersMaster login credentials
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

 /*
 * LGN001Repository
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

 package ph.com.project01.webportal.repository;

 import ph.com.project01.webportal.model.UsersMaster;
 import jakarta.transaction.Transactional;
 import java.time.LocalDateTime;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Modifying;
 import org.springframework.stereotype.Repository;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.query.Param;
 
 @Repository
 public interface LGN001Repository extends JpaRepository<UsersMaster, Long> {
 
     @Query(value = "SELECT * FROM project01.user_mst " + 
                    "WHERE account_id = ?", 
            nativeQuery = true)
     UsersMaster findByAccountId(String accountId);
 
     @Modifying 
     @Transactional
     @Query("UPDATE UsersMaster u SET u.invalidTry = :attempts " +
            "WHERE u.accountId = :accountId")
     void updateInvalidTry(
         @Param("accountId") String accountId, 
         @Param("attempts") int attempts
     );
 
     @Modifying 
     @Transactional
     @Query("UPDATE UsersMaster u SET u.invalidTry = :attempts, " +
            "u.lockFlag = :lockFlag " +
            "WHERE u.accountId = :accountId")
     void updateInvalidTryAndLockFlag(
         @Param("accountId") String accountId, 
         @Param("attempts") int attempts, 
         @Param("lockFlag") int lockFlag
     );
 
     @Modifying 
     @Transactional
     @Query("UPDATE UsersMaster u SET u.lastLoginDate = :lastLoginDate " +
            "WHERE u.accountId = :accountId")
     void updateLastLoginDate(
         @Param("accountId") String accountId, 
         @Param("lastLoginDate") LocalDateTime lastLoginDate
     );
 
     @Query("SELECT u.delFlag FROM UsersMaster u " + 
            "WHERE u.accountId = :accountId")
     Integer findDelFlagByAccountId(
         @Param("accountId") String accountId
     );
 }
 