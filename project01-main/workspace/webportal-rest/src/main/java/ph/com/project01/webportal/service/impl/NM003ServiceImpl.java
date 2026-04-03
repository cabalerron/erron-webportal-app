/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;

import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.repository.impl.NM003RepositoryImpl ;
import ph.com.project01.webportal.service.NM003Service ;

@Service
public class NM003ServiceImpl implements NM003Service{

    @Autowired
    NM003RepositoryImpl nm003repositoryImpl;

    @Override
    public String getScreenTitle() {
      StringBuilder screenTitle = new StringBuilder();
      String tier1 = nm003repositoryImpl.getFunctionName("12").split(" ")[0];
      String tier2 = nm003repositoryImpl.getFunctionName("112");
      screenTitle.append(tier1).append(" ").append(tier2);
      return screenTitle.toString();
    }

    @Override
    public News getNews(String sNewsId){
        News newsItem = nm003repositoryImpl.getNewsItem(sNewsId);
        return newsItem;
    }

    @Override
    public void updateNewsItem(News item){

        nm003repositoryImpl.updateNewsItem( item );

    }
}
