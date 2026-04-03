/*
 * UM003RepositoryImpl.java
 * User Edit Repository Implementation
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.repository.UM003Repository;

@Repository
public class UM003RepositoryImpl implements UM003Repository {

  private final JdbcTemplate jdbcTemplate;

  public UM003RepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void updateUser(UserMaster userMaster, 
  UserInfo userInfo, 
  String loggedInUserName) {
    
    String user_mst_sql = "UPDATE project01.user_mst SET "
      + "account_id = ?, "
      + "associate_id = ?, "
      + "position_id = ?, "
      + "department_id = ?, "
      + "section_id = ?, "
      + "status_id = ?, "
      + "role_id = ?, "
      + "mailaddress = ?, "
      + "del_flag = ?, "
      + "create_id = ?, "
      + "create_date = ?, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE user_id = ?";

    Object[] user_mst_params = {
      userMaster.getAccountId(),
      userMaster.getAssociateId(),
      userMaster.getPositionId(),
      userMaster.getDepartmentId(),
      userMaster.getSectionId(),
      userMaster.getStatusId(),
      userMaster.getRoleId(),
      userMaster.getMailaddress(),
      userMaster.getDelFlag(),
      userMaster.getCreateId(),
      userMaster.getCreateDate(),
      loggedInUserName,
      userMaster.getUserId()
    }; 

    jdbcTemplate.update(user_mst_sql, user_mst_params);

    String user_info_sql = "UPDATE project01.user_info SET "
      + "first_name = ?, "
      + "middle_name = ?, "
      + "last_name = ?, "
      + "img_path = ?, "
      + "del_flag = ?, "
      + "create_id = ?, "
      + "create_date = ?, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE user_id = ?";

    Object[] user_info_params = {
      userInfo.getFirstName(),
      userInfo.getMiddleName(),
      userInfo.getLastName(),
      userInfo.getImgPath(),
      userInfo.getDelFlag(),
      userInfo.getCreateId(),
      userInfo.getCreateDate(),
      loggedInUserName,
      userInfo.getUserId()
    };

    try{
      jdbcTemplate.update(user_info_sql, user_info_params);
    }
    catch(Exception e){
      e.printStackTrace();
    }

  }

  @Override
  public void unlockUser(int userId, String loggedInUsername) {
    String user_mst_sql = "UPDATE project01.user_mst SET "
      + "lock_flag = 0, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE user_id = ?";
    try{
      jdbcTemplate.update(user_mst_sql, loggedInUsername, userId);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

}
