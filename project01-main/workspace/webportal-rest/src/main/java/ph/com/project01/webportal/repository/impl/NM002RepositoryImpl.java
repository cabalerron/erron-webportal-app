/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to execute a SQL query that will add news in the database.
 * 
 */
package ph.com.project01.webportal.repository.impl;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.model.AddNews;
import ph.com.project01.webportal.repository.NM002Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NM002RepositoryImpl implements NM002Repository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long addNews(AddNews addNews) {
        String query = "INSERT INTO project01.news (title, content, image_path, start_date, end_date, del_flag, create_id, create_date, update_id, update_date) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING news_id"; 

        try {
            return jdbcTemplate.queryForObject(query, Long.class, 
                    addNews.getTitle(),
                    addNews.getContent(),
                    addNews.getImage_path(),
                    addNews.getStart_date(),
                    addNews.getEnd_date(),
                    addNews.getDel_flag(),
                    addNews.getCreate_id(),
                    addNews.getCreate_date(),
                    addNews.getUpdate_id(),
                    addNews.getUpdate_date()
            );
        } catch (Exception e) {
            throw new RuntimeException(PortalConstants.ERR_NEWS_ADD, e);
        } 
    }
}
