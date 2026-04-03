/*
 * RM003
 * Role Edit Repository Interface
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository;
import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RM003Repository {
    List<RoleMaster> getRoleDetail(Integer roleId);
    List<PermissionMaster> getPermissionList(Integer roleId);
    List<FunctionMaster> getFunctionList();
    void editRole(RoleMaster roleMaster, PermissionMaster permissionMaster);
}
