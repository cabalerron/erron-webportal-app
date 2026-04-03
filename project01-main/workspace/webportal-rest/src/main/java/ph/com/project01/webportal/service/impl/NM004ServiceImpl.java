/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 */

package ph.com.project01.webportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;

import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.repository.impl.NM004RepositoryImpl ;
import ph.com.project01.webportal.service.NM004Service ;

@Service
public class NM004ServiceImpl implements NM004Service{

    @Autowired
    NM004RepositoryImpl nm004repositoryImpl;

    @Override
    public String getScreenTitle() {
      StringBuilder screenTitle = new StringBuilder();
      String tier1 = nm004repositoryImpl.getFunctionName("12").split(" ")[0];
      String tier2 = nm004repositoryImpl.getFunctionName("113");
      screenTitle.append(tier1).append(" ").append(tier2);
      return screenTitle.toString();
    }

    @Override
    public News getNews(String sNewsId){
        News newsItem = nm004repositoryImpl.getNewsItem(sNewsId);
        return newsItem;
    }

    @Override
    public void deleteNews(
        int newsId, 
        String loggedInUserName){

        int result = nm004repositoryImpl.deleteNews(newsId, loggedInUserName);

        if (result == 0) {
            throw new RuntimeException("News not found");
          }
          if (result == -1) {
            throw new RuntimeException("Error in deleting news");
        }
    }
    
}
