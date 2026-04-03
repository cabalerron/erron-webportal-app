/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 */

package ph.com.project01.webportal.repository;

import org.springframework.stereotype.Repository ;

import ph.com.project01.webportal.model.News ;

@Repository
public interface NM004Repository {

    String getFunctionName(String functionCode);
    
    News getNewsItem(String sNewsID);

    int deleteNews(
        int newsId, 
        String loggedInUserName);
    
}
