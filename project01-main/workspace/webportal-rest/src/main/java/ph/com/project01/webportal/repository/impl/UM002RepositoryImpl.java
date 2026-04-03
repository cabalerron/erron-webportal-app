/*
 * UM002RepositoryImpl.java
 * User Registration Repository Implementation
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.repository.UM002Repository;

@Repository
public class UM002RepositoryImpl implements UM002Repository {

  private final JdbcTemplate jdbcTemplate;

  public UM002RepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

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

  @Override
  public UserMaster getUserMaster(int userId) {
    String sql = "SELECT "
    + "user_id, "
    + "account_id, "
    + "associate_id, "
    + "position_id, "
    + "department_id, "
    + "section_id, "
    + "status_id, "
    + "role_id, "
    + "mailaddress, "
    + "lock_flag, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_mst "
    + "WHERE user_id = ?";

    try {
      RowMapper<UserMaster> rowMapper 
      = new BeanPropertyRowMapper<>(UserMaster.class);
      UserMaster item = jdbcTemplate.queryForObject(sql, rowMapper, userId);

      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public UserInfo getUserInfo(int userId) {
    String sql = "SELECT " 
    + "p_id, "
    + "user_id, "
    + "first_name, "
    + "middle_name, "
    + "last_name, "
    + "img_path, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_info "
    + "WHERE user_id = ?";

    try {
      RowMapper<UserInfo> rowMapper 
      = new BeanPropertyRowMapper<>(UserInfo.class);
      UserInfo item = jdbcTemplate.queryForObject(sql, rowMapper, userId);

      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public List<RoleMaster> getAllRole() {
    String sql = "SELECT "
    + "role_id, "
    + "role_name, "
    + "role_sh_name, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.role_mst "
    + "WHERE del_flag = 0";

    try {
      RowMapper<RoleMaster> rowMapper 
      = new BeanPropertyRowMapper<>(RoleMaster.class);
      List<RoleMaster> items = jdbcTemplate.query(sql, rowMapper);

      return items;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<PositionMaster> getAllPosition() {
    String sql = "SELECT "
    + "position_id, "
    + "position_name, "
    + "position_sh_name, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.position_mst "
    + "WHERE del_flag = 0";

    try {
      RowMapper<PositionMaster> rowMapper 
      = new BeanPropertyRowMapper<>(PositionMaster.class);
      List<PositionMaster> items = jdbcTemplate.query(sql, rowMapper);

      return items;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<DepartmentMaster> getAllDepartment() {
    String sql = "SELECT "
    + "department_id, "
    + "department_name, "
    + "department_sh_name, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.department_mst "
    + "WHERE del_flag = 0";

    try {
      RowMapper<DepartmentMaster> rowMapper 
      = new BeanPropertyRowMapper<>(DepartmentMaster.class);
      List<DepartmentMaster> items = jdbcTemplate.query(sql, rowMapper);

      return items;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<SectionMaster> getAllSection() {
    String sql = "SELECT "
    + "section_id, "
    + "section_name, "
    + "section_sh_name, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.section_mst "
    + "WHERE del_flag = 0";

    try {
      RowMapper<SectionMaster> rowMapper 
      = new BeanPropertyRowMapper<>(SectionMaster.class);
      List<SectionMaster> items = jdbcTemplate.query(sql, rowMapper);

      return items;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<UserStatus> getAllUserStatus() {
    String sql = "SELECT "
    + "status_id, "
    + "status_name, "
    + "status_desc, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_status "
    + "WHERE del_flag = 0";

    try {
      RowMapper<UserStatus> rowMapper 
      = new BeanPropertyRowMapper<>(UserStatus.class);
      List<UserStatus> items = jdbcTemplate.query(sql, rowMapper);

      return items;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public int insertUser(UserMaster userMaster, UserInfo userInfo) {

    // insert user_mst
    String user_mst_sql = "INSERT INTO project01.user_mst ("
    + "account_id, "
    + "associate_id, "
    + "password, "
    + "position_id, "
    + "department_id, "
    + "section_id, "
    + "status_id, "
    + "role_id, "
    + "mailaddress, "
    + "invalid_try, "
    + "create_id, "
    + "update_id) "
    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    userMaster.setInvalidTry(0);
    Object[] user_mst_params = {
        userMaster.getAccountId(),
        userMaster.getAssociateId(),
        userMaster.getPassword(),
        userMaster.getPositionId(),
        userMaster.getDepartmentId(),
        userMaster.getSectionId(),
        userMaster.getStatusId(),
        userMaster.getRoleId(),
        userMaster.getMailaddress(),
        userMaster.getInvalidTry(),
        userMaster.getCreateId(),
        userMaster.getUpdateId()
    };

    int result = 0;

    try{ 
      result = jdbcTemplate.update(user_mst_sql, user_mst_params);
    } catch (Exception e) {
      return -1;
    }

    if (result > 0) {
      // return the primary key of the inserted row
      int lastIdCreated = jdbcTemplate.queryForObject
      ("SELECT currval('project01.user_mst_user_id_seq')", Integer.class);

      // insert user_info
      String user_info_sql = "INSERT INTO project01.user_info ("
      + "user_id, "
      + "first_name, "
      + "middle_name, "
      + "last_name, "
      + "img_path, "
      + "create_id, "
      + "update_id) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?)";

      Object[] user_info_params = {
          lastIdCreated,
          userInfo.getFirstName(),
          userInfo.getMiddleName(),
          userInfo.getLastName(),
          userInfo.getImgPath(),
          userInfo.getCreateId(),
          userInfo.getUpdateId()
      };

      try{
        result = jdbcTemplate.update(user_info_sql, user_info_params);
      } catch (Exception e) {
        return -1;
      }

      return result;

    }
    else {
      return -1;
    }

  }

  @Override
  public String getNextAssociateId() {
    String sql = "SELECT max(associate_id) FROM project01.user_mst";
    String lastAssociateId = jdbcTemplate.queryForObject(sql, String.class);
    return lastAssociateId;
  }

  @Override
  public UserMaster getUserMasterByAssociateId(String associateId) {
    String sql = "SELECT "
    + "user_id, "
    + "account_id, "
    + "associate_id, "
    + "position_id, "
    + "department_id, "
    + "section_id, "
    + "status_id, "
    + "role_id, "
    + "mailaddress, "
    + "lock_flag, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_mst "
    + "WHERE associate_id = ?";
    try {
      RowMapper<UserMaster> rowMapper 
      = new BeanPropertyRowMapper<>(UserMaster.class);
      UserMaster item = jdbcTemplate
      .queryForObject(sql, rowMapper, associateId);

      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public UserMaster getUserMasterByEmail(String mail) {
    String sql = "SELECT "
    + "user_id, "
    + "account_id, "
    + "associate_id, "
    + "position_id, "
    + "department_id, "
    + "section_id, "
    + "status_id, "
    + "role_id, "
    + "mailaddress, "
    + "lock_flag, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_mst "
    + "WHERE mailaddress = ?";
    try {
      RowMapper<UserMaster> rowMapper 
      = new BeanPropertyRowMapper<>(UserMaster.class);
      UserMaster item = jdbcTemplate.queryForObject(sql, rowMapper, mail);

      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public UserMaster getUserMasterByUsername(String username) {
    String sql = "SELECT "
    + "user_id, "
    + "account_id, "
    + "associate_id, "
    + "position_id, "
    + "department_id, "
    + "section_id, "
    + "status_id, "
    + "role_id, "
    + "mailaddress, "
    + "lock_flag, "
    + "del_flag, "
    + "create_id, "
    + "create_date, "
    + "update_id, "
    + "update_date "
    + "FROM project01.user_mst "
    + "WHERE account_id = ?";
    try {
      RowMapper<UserMaster> rowMapper 
      = new BeanPropertyRowMapper<>(UserMaster.class);
      UserMaster item = jdbcTemplate.queryForObject(sql, rowMapper, username);

      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

}
