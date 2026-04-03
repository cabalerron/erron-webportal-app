/*
 * RM002
 * Role Registration Repository Implementation
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.repository.RM002Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RM002RepositoryImpl implements RM002Repository {

  @Autowired
  JdbcTemplate jdbcTemplate;

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

  //Get next Role ID
  public String getNextRoleId() {
    try{
      String sql = "SELECT max(role_id) FROM project01.role_mst";
      String lastRoleId = jdbcTemplate.queryForObject(sql, 
          String.class);
      return lastRoleId;
    } catch (DataAccessException ex) {
      log.error("Error retrieving next role ID", ex);
      return null;
    }
    
  }

  //Insert Role to role_mst and permission table
  public void insertRole(RoleMaster roleMaster, 
        PermissionMaster permissionMaster) {
    try{
      String role_mst_sql = "INSERT INTO project01.role_mst(" + 
            "role_id, role_name, role_sh_name, create_id, update_date) " + 
            "VALUES (?, ?, ?, ?, ?)";

      Object[] role_mst_params = {
        roleMaster.getRoleId(),
        roleMaster.getRoleName(),
        roleMaster.getRoleShName(),
        roleMaster.getCreateId(),
        null
      };

      int result = jdbcTemplate.update(role_mst_sql, role_mst_params);
      if(result == 1){
        String permission_sql = "INSERT INTO project01.permission(" + 
                "function_code, role_id, create_id, update_date) " + 
                "VALUES (?, ?, ?, ?)";

        String[] functionCodes = permissionMaster.getFunctionCode();
        
        for(String functionCode: functionCodes){
          int parsedFunctionCode = Integer.parseInt(functionCode);
          Object[] permission_params = {
            parsedFunctionCode,
            permissionMaster.getRoleId(),
            permissionMaster.getCreateId(),
            null
          };
          jdbcTemplate.update(permission_sql, permission_params);
        }
      }
    } catch (DataAccessException ex) {
      log.error("Error inserting role and permissions", ex);
    }
  }

  //Get Role Name 
  public RoleMaster getRoleMasterByRoleName(String roleName) {
    String sql = "SELECT role_id, role_name, role_sh_name, del_flag," + 
            " create_id, create_date, update_id, update_date" + 
            " FROM project01.role_mst WHERE role_name = ?";
    try{
      RowMapper<RoleMaster> rowMapper = new BeanPropertyRowMapper<>(
            RoleMaster.class);
      RoleMaster item = jdbcTemplate.queryForObject(sql, rowMapper, roleName);
      return item;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

}
