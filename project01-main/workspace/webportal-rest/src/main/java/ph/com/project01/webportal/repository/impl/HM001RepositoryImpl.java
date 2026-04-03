/*
 * HM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.News;
import ph.com.project01.webportal.repository.HM001Respository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HM001RepositoryImpl implements HM001Respository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<News> getNewArticle() {
        String query = "SELECT news_id, title, news_content, image_path, start_date, end_date, del_flag, create_id, create_date, update_id, update_date FROM project01.news ";

        try {
            RowMapper<News> rowMapper = new BeanPropertyRowMapper<>(News.class);
            List<News> ArticleList = jdbcTemplate.query(query, rowMapper);

            return ArticleList;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

     public String getHM001FunctionName(String functionCode) {
        String sql = 
        "SELECT function_name "
        + "FROM project01.function_mst " 
        + "WHERE function_code = ?";

        try {
        String functionName = jdbcTemplate
        .queryForObject(sql, String.class, functionCode);
        return functionName;
        } catch (EmptyResultDataAccessException e) {
        return null;
        }
    }

}