/*
 * RM002
 * Role Registration Service Implementation
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.repository.impl.RM002RepositoryImpl;
import ph.com.project01.webportal.service.RM002Service;

@Service
public class RM002ServiceImpl implements RM002Service {

    @Autowired
    RM002RepositoryImpl RM002repositoryImpl;

    @Override
    public List<FunctionMaster> getFunctionList() {
        return RM002repositoryImpl.getFunctionList();
    }

    @Override
    public String getNextRoleId() {
    String nextRoleId = RM002repositoryImpl.getNextRoleId();
    
    nextRoleId = String.valueOf(Integer.parseInt(nextRoleId) + 1);

    return nextRoleId;
    
    }

    @Override
    public String findExistingRecords(String roleName) {
        RoleMaster roleMaster = 
                RM002repositoryImpl.getRoleMasterByRoleName(roleName);
        if(roleMaster != null){
            return "Role Name";
        }
        return null;
    }

    @Override
        public void registerRole(RoleMaster roleMaster, 
                PermissionMaster permissionMaster, String loggedInUsername) {
        
        roleMaster.setCreateId(loggedInUsername);
        permissionMaster.setCreateId(loggedInUsername);

        RM002repositoryImpl.insertRole(roleMaster, permissionMaster);

    }
}
