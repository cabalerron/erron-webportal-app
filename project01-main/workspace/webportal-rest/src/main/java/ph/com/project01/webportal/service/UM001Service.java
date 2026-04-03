package ph.com.project01.webportal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ph.com.project01.webportal.dto.UM001OutDto;
import ph.com.project01.webportal.dto.searchUserDTO;
import ph.com.project01.webportal.model.Pagination;

public interface UM001Service {

    List<UM001OutDto> getUserList();
    List<UM001OutDto> findUser(searchUserDTO searchCriteria);
    String getScreenTitle();
    Page<UM001OutDto> deleteMultipleItems(List<String> sItems, Pagination pagination);

}
