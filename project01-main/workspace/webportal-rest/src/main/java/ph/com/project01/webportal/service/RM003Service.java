/*
 * RM003
 * Role Edit Service Interface
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;

import java.util.List;

public interface RM003Service {
    List<RoleMaster> getRoleDetail(Integer roleId);
    List<PermissionMaster> getPermissionList(Integer roleId);
    List<FunctionMaster> getFunctionList();
    public void editRole(String loggedInUsername, RoleMaster roleMaster, 
                    PermissionMaster permissionMaster);
}