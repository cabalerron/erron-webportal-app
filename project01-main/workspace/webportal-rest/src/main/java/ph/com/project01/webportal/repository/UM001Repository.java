package ph.com.project01.webportal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.dto.UM001OutDto;
import ph.com.project01.webportal.dto.searchUserDTO;
import ph.com.project01.webportal.model.Pagination;

@Repository
public interface UM001Repository {
    List<UM001OutDto> getUserList();
    List<UM001OutDto> findUserByCriteria(searchUserDTO searchCriteria);
    String getFunctionName(String functionCode);
    Page<UM001OutDto> deleteItems(List<String> sItems, Pagination page);
}

