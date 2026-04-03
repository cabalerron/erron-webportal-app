/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.repository;
import ph.com.project01.webportal.model.News;
import ph.com.project01.webportal.model.Pagination ;

import java.util.List ;

import org.springframework.data.domain.Page ;
import org.springframework.stereotype.Repository;

@Repository
public interface NM001Repository {
    String getFunctionName(String functionCode);
    Page<News> searchNews(String sNewsId, String sTitle, String sCreateBy, Pagination pagination);
    Page<News> getNewsList(String loggedInUsername, String loggedInUserRole, Pagination pagination);
    Page<News> deleteItems(List<String> sItems, Pagination pagination);
}
