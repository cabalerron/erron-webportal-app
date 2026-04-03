/*
 * RM004
 * Role Deletion Service Interface
 *
 * v 00.001 - 10/31/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;

import java.util.List;

public interface RM004Service {
    List<RoleMaster> getRoleDetail(Integer roleId);
    List<PermissionMaster> getPermissionList(Integer roleId);
    List<FunctionMaster> getFunctionList();
    public void deleteRole(Integer roleId, String loggedInUsername);
}
