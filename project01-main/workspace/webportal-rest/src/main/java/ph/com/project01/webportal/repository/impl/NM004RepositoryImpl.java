/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 */

package ph.com.project01.webportal.repository.impl;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.dao.EmptyResultDataAccessException ;
import org.springframework.jdbc.core.BeanPropertyRowMapper ;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.core.RowMapper ;
import org.springframework.stereotype.Repository ;

import lombok.RequiredArgsConstructor ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.repository.NM004Repository ;


@Repository
@RequiredArgsConstructor
public class NM004RepositoryImpl implements NM004Repository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String getFunctionName(String functionCode) {
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

    public News getNewsItem(String sNewsID){

        String sql = "SELECT * FROM project01.news where news_id = ? " ;

        try{
            RowMapper<News> rowMapper = new BeanPropertyRowMapper<>(News.class);
            News item = jdbcTemplate.queryForObject(sql, rowMapper, Integer.parseInt(sNewsID));
            return item;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }   
    }

    @Override
    public int deleteNews(
        int newsId, 
        String loggedInUserName){

        int result = 0;

        try{
            // Update delete flag to 1
            String sql = " UPDATE project01.news SET "
            + "del_flag = 1, "
            + "update_id = ?, "
            + "update_date = DEFAULT "
            + " WHERE news_id = ? ";

            result = jdbcTemplate.update(sql, loggedInUserName, newsId);

        }catch(Exception e){
            return -1;
        }

        return result;

    }

    
}
