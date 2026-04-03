/*
 * RM003
 * Role Edit Service Implementation
 *
 * v 00.001 - 11/06/2024
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
import ph.com.project01.webportal.repository.impl.RM003RepositoryImpl;
import ph.com.project01.webportal.service.RM003Service;

@Service
public class RM003ServiceImpl implements RM003Service {

    @Autowired
    RM003RepositoryImpl RM003repositoryImpl;

    @Override
    public List<RoleMaster> getRoleDetail(Integer roleId) {
        return RM003repositoryImpl.getRoleDetail(roleId);
    }

    @Override
    public List<PermissionMaster> getPermissionList(Integer roleId) {
        return RM003repositoryImpl.getPermissionList(roleId);
    }

    @Override
    public List<FunctionMaster> getFunctionList() {
        return RM003repositoryImpl.getFunctionList();
    }

    @Override
    public void editRole(String loggedInUsername, RoleMaster roleMaster, 
                    PermissionMaster permissionMaster) {

        roleMaster.setCreateId(loggedInUsername);
        roleMaster.setUpdateId(loggedInUsername);
        RM003repositoryImpl.editRole(roleMaster, permissionMaster);
    }

}
