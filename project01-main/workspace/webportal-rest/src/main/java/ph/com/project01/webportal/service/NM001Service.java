/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.News;
import ph.com.project01.webportal.model.Pagination ;

import java.util.List ;

import org.springframework.data.domain.Page ;


public interface NM001Service {

    String getScreenTitle();

    Page<News> getNewsList(
        String loggedInUsername, 
        String loggedInUserRole, 
        Pagination pagination);

    Page<News> NM001search(
        String sNewsId, 
        String sTitle, 
        String sCreateBy,
        Pagination pagination); 

    Page<News> deleteMultipleItems(List<String> sItems, Pagination pagination);
    
}
