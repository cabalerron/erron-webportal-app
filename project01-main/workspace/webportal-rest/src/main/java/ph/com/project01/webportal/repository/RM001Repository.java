/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.Pagination;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RM001Repository {
    Page<RoleMaster> searchRoleName(String sRoleName, Pagination pagination);
    Page<RoleMaster> getRoleList(String loggedInUsername, String loggedInUserRole, Pagination pagination);
    Page<RoleMaster> deleteItems(List<String> sItems, Pagination pagination);
}
