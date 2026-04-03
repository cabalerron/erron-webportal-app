/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to execute a SQL query that retrieves active news filtered by date and status, and sorted by the most recent update date.
 * 
 */
package ph.com.project01.webportal.repository.impl;

import ph.com.project01.webportal.model.NewsBulletin;
import ph.com.project01.webportal.repository.NM005Repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NM005RepositoryImpl implements NM005Repository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<NewsBulletin> getNewsBulletinList() {
        String query = "SELECT news_id, title, content, image_path, start_date, end_date, del_flag, create_id, create_date, update_id, update_date FROM project01.news " +
                "WHERE del_flag::integer = 0 " +
                "AND start_date <= CURRENT_DATE " +
                "AND end_date >= CURRENT_DATE " +
                "ORDER BY update_date DESC";
        try {
            RowMapper<NewsBulletin> rowMapper = new BeanPropertyRowMapper<>(NewsBulletin.class);
            return jdbcTemplate.query(query, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
