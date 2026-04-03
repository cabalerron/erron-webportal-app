/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service.impl;

import java.util.List ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.model.Pagination ;
import ph.com.project01.webportal.repository.impl.NM001RepositoryImpl ;
import ph.com.project01.webportal.service.NM001Service ;

import org.springframework.data.domain.Page ;

@Service
public class NM001ServiceImpl implements NM001Service {

    @Autowired
    NM001RepositoryImpl NM001repositoryImpl;

    @Override
    public String getScreenTitle() {
      StringBuilder screenTitle = new StringBuilder();
      String tier1 = NM001repositoryImpl.getFunctionName("12").split(" ")[0];
      String tier2 = NM001repositoryImpl.getFunctionName("114");
      screenTitle.append(tier1).append(" ").append(tier2);
      return screenTitle.toString();
    }


    @Override
    public Page<News> getNewsList(
        String loggedInUsername, 
        String loggedInUserRole, 
        Pagination pagination){
        Page<News> a = NM001repositoryImpl.getNewsList(
            loggedInUsername, loggedInUserRole, pagination);

        return a;
    }

    @Override
    public Page<News> NM001search(
        String sNewsId, 
        String sTitle, 
        String sCreateBy,
        Pagination pagination){
            
        Page<News> a  = NM001repositoryImpl.searchNews(
            sNewsId, sTitle, sCreateBy, pagination);

        return a;
    }

    @Override
    public Page<News> deleteMultipleItems(
        List<String> sItems, 
        Pagination pagination){

        Page<News> items = NM001repositoryImpl.deleteItems(
            sItems, pagination);
            
        return items;
    }

}
