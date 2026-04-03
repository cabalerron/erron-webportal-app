package ph.com.project01.webportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.UsersMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UsersMaster, Long> {

    @Query(value = "SELECT * FROM user_mst WHERE account_id = ?", nativeQuery = true)
    UsersMaster findByAccountId(String accountId);
}
