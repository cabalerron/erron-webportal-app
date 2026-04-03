/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList ;
import java.util.List ;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.dao.EmptyResultDataAccessException ;
import org.springframework.jdbc.core.BeanPropertyRowMapper ;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.core.RowMapper ;
import org.springframework.stereotype.Repository ;
import ph.com.project01.webportal.common.PortalConstants ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.model.Pagination ;
import ph.com.project01.webportal.repository.NM001Repository ;

import lombok.RequiredArgsConstructor ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest ;
import org.springframework.data.domain.Pageable;

@Repository
@RequiredArgsConstructor
public class NM001RepositoryImpl implements NM001Repository{

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
    
    /**
     * News items List
     */
    public Page<News> getNewsList(
        String loggedInUsername, 
        String loggedInUserRole, 
        Pagination page) {

        try{
            // Base query to select news
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.append(" SELECT * FROM project01.news ");
            sbSelect.append(" WHERE del_flag = 0 ORDER BY news_id ASC ");

            // Count query preparation
            StringBuilder sbCount = new StringBuilder();
            sbCount.append(" SELECT COUNT(*) FROM project01.news ");
            sbCount.append(" WHERE del_flag = 0 ");

            // Execute count query
            Long count = jdbcTemplate.queryForObject(
                sbCount.toString(), Long.class);

            // Define pagination
            Pageable pageable = PageRequest.of(
                page.getPage(), PortalConstants.PAGE_SIZE);

            // Append pagination LIMIT and OFFSET clauses to the main query
            sbSelect.append(" LIMIT ? OFFSET ?");
            // Add pagination parameters
            List<Object> params = new ArrayList<>();
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());

            // Create a row mapper for mapping database rows to News objects
            RowMapper<News> rowMapper = new BeanPropertyRowMapper<>(
                News.class);

            // Execute the main query to fetch the paginated results
            List<News> newsList = jdbcTemplate.query(
                sbSelect.toString(), rowMapper, params.toArray());

            // Create and return a Page object containing the paginated results
            Page<News> pageOut = new PageImpl<>(newsList, pageable, count);
            return pageOut ;
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search Items
     */
    public Page<News> searchNews(
        String sNewsId, 
        String sTitle, 
        String sCreateBy, 
        Pagination page) {

        try{
            // Create SELECT statement
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.append(" SELECT * FROM project01.news AS n " );
            
            // Build WHERE conditions
            StringBuilder sbCount = new StringBuilder();
            sbCount.append(" SELECT COUNT(*) FROM project01.news AS n " );

            StringBuilder sbWhere = new StringBuilder();
            List<Object> params = new ArrayList<>();
        
            // Add conditions dynamically (null and empty checks)
            if (sNewsId != null && !sNewsId.isEmpty()) {
                if (sbWhere.length() > 0) sbWhere.append(" AND ");
                sbWhere.append(" n.news_id = ? ");
                params.add(Integer.parseInt(sNewsId));
            }
            if (sTitle != null && !sTitle.isEmpty()) {
                if (sbWhere.length() > 0) sbWhere.append(" AND ");
                sbWhere.append(" n.title = ? ");
                params.add(sTitle);
            }
            if (sCreateBy != null && !sCreateBy.isEmpty()) {
                if (sbWhere.length() > 0) sbWhere.append(" AND ");
                sbWhere.append(" n.create_id = ? ");
                params.add(sCreateBy);
            }
        
            // Always check for del_flag = 0
            if (sbWhere.length() > 0) sbWhere.append(" AND ");
            sbWhere.append(" n.del_flag = 0 ");
        
            // Append WHERE condition to both count and select queries if exists
            if (sbWhere.length() > 0) {
                sbSelect.append(" WHERE ").append(sbWhere.toString());
                sbCount.append(" WHERE ").append(sbWhere.toString());
            }
        
            // Execute count query
            Long count = jdbcTemplate.queryForObject(
                sbCount.toString(), Long.class, params.toArray());
        
            // Define the pagination
            Pageable pageable = PageRequest.of(
                page.getPage(), PortalConstants.PAGE_SIZE);
        
            // Append pagination LIMIT and OFFSET clauses
            sbSelect.append(" LIMIT ? OFFSET ? ");
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());
        
            // Map the results to the News entity
            RowMapper<News> rowMapper = new BeanPropertyRowMapper<>(
                News.class);

            List<News> list = jdbcTemplate.query(
                sbSelect.toString(), rowMapper, params.toArray());
        
            // Create a Page object with the results
            return new PageImpl<>(list, pageable, count);

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete Multiple Items
     */
    public Page<News> deleteItems(
        List<String> sItems, 
        Pagination page ) {

        try{

            List<Integer> iItems = sItems.stream()
                                 .map(Integer::parseInt)
                                 .toList();
     
            String updateSql = "UPDATE project01.news SET del_flag = 1 WHERE news_id = ?";

            jdbcTemplate.batchUpdate(updateSql, iItems, iItems.size(),
            (ps, id) -> ps.setInt(1, id));

            String countSql = "SELECT COUNT(*) FROM project01.news WHERE del_flag = 0";
            Long count = jdbcTemplate.queryForObject(countSql, Long.class);

            Pageable pageable = PageRequest.of(page.getPage(), PortalConstants.PAGE_SIZE);

            StringBuilder sbSelect = new StringBuilder("SELECT * FROM project01.news WHERE del_flag = 0 ");
            sbSelect.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());

            RowMapper<News> rowMapper = new BeanPropertyRowMapper<>(News.class);

            List<News> newsList = jdbcTemplate.query(sbSelect.toString(), rowMapper, params.toArray());

            return new PageImpl<>(newsList, pageable, count);

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}