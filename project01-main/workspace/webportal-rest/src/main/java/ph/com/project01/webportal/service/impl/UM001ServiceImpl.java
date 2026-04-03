package ph.com.project01.webportal.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ph.com.project01.webportal.dto.UM001OutDto;
import ph.com.project01.webportal.dto.searchUserDTO;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.repository.impl.UM001RepositoryImpl;
import ph.com.project01.webportal.service.UM001Service;

@Service
public class UM001ServiceImpl implements UM001Service {

    @Autowired
    UM001RepositoryImpl um001repositoryImpl;

    public List<UM001OutDto> getUserList() {
        List<UM001OutDto> userList = um001repositoryImpl.getUserList();
        return userList;
    }

    public List<UM001OutDto> findUser(searchUserDTO searchCriteria) {
        List<UM001OutDto> searchUser = um001repositoryImpl.findUserByCriteria(searchCriteria);
        return searchUser;
    }

    public String getScreenTitle() {
        StringBuilder screenTitle = new StringBuilder();
        String tier1 = um001repositoryImpl.getFunctionName("11").split(" ")[0];
        String tier2 = um001repositoryImpl.getFunctionName("114");
        screenTitle.append(tier1).append(" ").append(tier2);
        return screenTitle.toString();
    }

    // Service: deleteUsers for multiple users
    public Page<UM001OutDto> deleteMultipleItems(List<String> sItems, Pagination pagination){
        Page<UM001OutDto> items = um001repositoryImpl.deleteItems(sItems, pagination);
        return items;
    }   
}