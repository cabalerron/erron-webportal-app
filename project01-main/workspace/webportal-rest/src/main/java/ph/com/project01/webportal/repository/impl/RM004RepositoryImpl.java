/*
 * RM004
 * Role Deletion Repository Implementation
 *
 * v 00.001 - 10/31/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.repository.RM004Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RM004RepositoryImpl implements RM004Repository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<RoleMaster> getRoleDetail(Integer roleId) {
    try {
      StringBuilder sbSelect = new StringBuilder("SELECT * FROM " +
            "project01.role_mst WHERE del_flag = 0 AND role_id = ?");
      RowMapper<RoleMaster> rowMapper = new BeanPropertyRowMapper<>(
        RoleMaster.class);
      List<RoleMaster> roleDetail = jdbcTemplate.query(
            sbSelect.toString(), rowMapper, roleId);
      return roleDetail;
    } catch (DataAccessException e) {
        log.error("Error retrieving role details", e);
        return new ArrayList<>();
    }
}


  //Get all active functions
  public List<PermissionMaster> getPermissionList(Integer roleId) {
    StringBuilder sbSelect = new StringBuilder("SELECT * FROM " +
            "project01.permission WHERE del_flag = 0 AND role_id = ?");
    try {
      RowMapper<PermissionMaster> rowMapper = new BeanPropertyRowMapper<>(
        PermissionMaster.class);
        List<PermissionMaster> permissionList = jdbcTemplate.query(
          sbSelect.toString(), rowMapper, roleId);
      return permissionList;
    } catch (DataAccessException e) {
      log.error("Error retrieving function list", e);
      return new ArrayList<>();
    }
  }

  //Get all active functions
  public List<FunctionMaster> getFunctionList() {
    try {
      StringBuilder sbSelect = new StringBuilder("SELECT * FROM " +
            "project01.function_mst WHERE del_flag = 0 ORDER BY function_code");
      RowMapper<FunctionMaster> rowMapper = new BeanPropertyRowMapper<>(
            FunctionMaster.class);
      List<FunctionMaster> functionList = jdbcTemplate.query(
            sbSelect.toString(), rowMapper);
      return functionList;
    } catch (DataAccessException e) {
      log.error("Error retrieving function list", e);
      return new ArrayList<>();
    }
  }
  
  @Override
  public void deleteRole(Integer roleId, String loggedInUsername) {
    try{
      // Update delete flag to 1
      String role_mst_sql = "UPDATE project01.role_mst SET "
      + "del_flag = 1, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE role_id = ?";
      jdbcTemplate.update(role_mst_sql, loggedInUsername, roleId);

      String permission_sql = "UPDATE project01.permission SET "
      + "del_flag = 1, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE role_id = ?";
      jdbcTemplate.update(permission_sql, loggedInUsername, roleId);
    }
    catch(Exception e){
      e.printStackTrace();
    }

  }

}
