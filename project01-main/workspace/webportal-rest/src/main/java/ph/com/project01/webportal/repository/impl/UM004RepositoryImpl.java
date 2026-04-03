/*
 * UM004RepositoryImpl.java
 * User Delete Repository Implementation
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.repository.UM004Repository;

@Repository
public class UM004RepositoryImpl implements UM004Repository{

  private final JdbcTemplate jdbcTemplate;

  public UM004RepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int deleteUser(int userId, String loggedInUserName) {
    int result = 0;
    try{
      // Update delete flag to 1
      String user_mst_sql = "UPDATE project01.user_mst SET "
      + "del_flag = 1, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE user_id = ?";
      

      String user_info_sql = "UPDATE project01.user_info SET "
      + "del_flag = 1, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE user_id = ?";

      result = jdbcTemplate.update(user_mst_sql, loggedInUserName, userId);

      if(result == 1){
        result = jdbcTemplate.update(user_info_sql, loggedInUserName, userId);
      }
      else {
        return 0;
      }
    }
    catch(Exception e){
      return -1;
    }
    return result;

  }
  
}
