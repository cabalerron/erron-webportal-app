/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.repository.impl;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.dao.EmptyResultDataAccessException ;
import org.springframework.jdbc.core.BeanPropertyRowMapper ;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.core.RowMapper ;
import org.springframework.stereotype.Repository ;

import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.repository.NM003Repository ;

import lombok.RequiredArgsConstructor ;

@Repository
@RequiredArgsConstructor
public class NM003RepositoryImpl implements NM003Repository{

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

    public int updateNewsItem(News item){

        try{

            StringBuilder sb = new StringBuilder() ;

            sb.append(" UPDATE project01.news SET ");

            sb.append(" title = ?, ");
            sb.append(" content = ?, ");
            sb.append(" image_path = ?, ");
            sb.append(" start_date = ?, ");
            sb.append(" end_date = ?, ");
            sb.append(" del_flag = ?, ");
            sb.append(" update_id = ?, ");
            sb.append(" update_date = ? ");
            sb.append( " WHERE news_id = ? " ) ;

            int result = jdbcTemplate.update(
                sb.toString(),
                item.getTitle(),
                item.getContent(),
                item.getImagePath(),
                item.getStartDate(),
                item.getEndDate(),
                item.getDelFlag(),
                item.getUpdateId(),
                item.getUpdateDate(),
                item.getNewsId());

            return result ;

        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
