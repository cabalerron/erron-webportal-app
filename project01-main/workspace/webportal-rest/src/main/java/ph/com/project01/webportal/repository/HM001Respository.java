/*
 * HM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.News;

@Repository
public interface HM001Respository {

    List<News> getNewArticle();
    String getHM001FunctionName(String functionCode);

}