/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.repository.impl.RM001RepositoryImpl;
import ph.com.project01.webportal.service.RM001Service;

@Service
public class RM001ServiceImpl implements RM001Service {

    @Autowired
    RM001RepositoryImpl RM001repositoryImpl;

    @Override
    public Page<RoleMaster> getRoleList(String loggedInUsername, String loggedInUserRole, Pagination pagination){
        Page<RoleMaster> a = RM001repositoryImpl.getRoleList(loggedInUsername, loggedInUserRole, pagination);
        return a;
    }

    @Override
    public Page<RoleMaster> searchRoleName(
        String sRoleName, 
        Pagination pagination){
            
            Page<RoleMaster> a  = RM001repositoryImpl.searchRoleName(sRoleName,  pagination);
            return a;
    }

    @Override
    public Page<RoleMaster> deleteMultipleItems(List<String> sItems, Pagination pagination){
        Page<RoleMaster> items = RM001repositoryImpl.deleteItems(sItems, pagination);
        return items;
    }
}
