/*
 * RM002
 * Role Registration Service Interface
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;

import java.util.List;

public interface RM002Service {
    List<FunctionMaster> getFunctionList();
    String getNextRoleId();
    String findExistingRecords(String roleName);
    public void registerRole(RoleMaster roleMaster, 
            PermissionMaster permissionMaster, String loggedInUserId);

}
