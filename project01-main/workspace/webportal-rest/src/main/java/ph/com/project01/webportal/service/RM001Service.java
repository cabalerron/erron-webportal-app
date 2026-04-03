/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.Pagination;

import java.util.List;

import org.springframework.data.domain.Page;

public interface RM001Service {
    Page<RoleMaster> getRoleList(String loggedInUsername, String loggedInUserRole, Pagination pagination);

    Page<RoleMaster> searchRoleName(
        String sRoleName, 
        Pagination pagination); 

    Page<RoleMaster> deleteMultipleItems(List<String> sItems, Pagination pagination);
}
