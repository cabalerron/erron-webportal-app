/*
 * RM004
 * Role Deletion Service Implementation
 *
 * v 00.001 - 10/31/2024
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
import ph.com.project01.webportal.repository.impl.RM004RepositoryImpl;
import ph.com.project01.webportal.service.RM004Service;

@Service
public class RM004ServiceImpl implements RM004Service {

    @Autowired
    RM004RepositoryImpl RM004repositoryImpl;

    @Override
    public List<RoleMaster> getRoleDetail(Integer roleId) {
        return RM004repositoryImpl.getRoleDetail(roleId);
    }

    @Override
    public List<PermissionMaster> getPermissionList(Integer roleId) {
        return RM004repositoryImpl.getPermissionList(roleId);
    }

    @Override
    public List<FunctionMaster> getFunctionList() {
        return RM004repositoryImpl.getFunctionList();
    }

    @Override
    public void deleteRole(Integer roleId, String loggedInUsername) {
        RM004repositoryImpl.deleteRole(roleId, loggedInUsername);
    }

}
