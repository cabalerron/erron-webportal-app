/*
 * UM005Repository
 * Repository for UsersMaster password update
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.repository;

import ph.com.project01.webportal.model.UsersMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UM005Repository extends JpaRepository<UsersMaster, Long> {

@Query("SELECT u.password FROM UsersMaster u WHERE u.accountId = :accountId")
String findPasswordByAccountId(@Param("accountId") String accountId);

@Modifying
@Transactional
@Query("UPDATE UsersMaster u SET u.password = :newPassword " +
       "WHERE u.accountId = :accountId")
void updatePassword(
       @Param("accountId") String accountId, 
       @Param("newPassword") String newPassword
);
}
 
 