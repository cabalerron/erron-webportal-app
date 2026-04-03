/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.News ;

public interface NM003Service {

    String getScreenTitle();

    News getNews(String sNewsId);

    void updateNewsItem(News item);
    
    
}
