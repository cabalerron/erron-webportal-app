/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 */

package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.News ;

public interface NM004Service {

    String getScreenTitle();

    News getNews(String sNewsId);

    public void deleteNews(
        int newsId, 
        String loggedInUserName);
    
}
