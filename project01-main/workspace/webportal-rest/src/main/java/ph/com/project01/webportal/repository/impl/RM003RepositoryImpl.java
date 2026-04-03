/*
 * RM003
 * Role Edit Repository Implementation
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.repository.RM003Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RM003RepositoryImpl implements RM003Repository {

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
  public void editRole(RoleMaster roleMaster, 
                                PermissionMaster permissionMaster) {
    try{

    String role_mst_sql = "UPDATE project01.role_mst SET "
      + "role_name = ?, "
      + "role_sh_name = ?, "
      + "update_id = ?, "
      + "update_date = DEFAULT "
      + "WHERE role_id = ?";

      Object[] role_mst_params = {
        roleMaster.getRoleName(),
        roleMaster.getRoleShName(),
        roleMaster.getUpdateId(),
        roleMaster.getRoleId()
      }; 

      int result = jdbcTemplate.update(role_mst_sql, role_mst_params);
      if(result == 1){
        List<PermissionMaster> existingPermissions = 
                  getPermissionList(roleMaster.getRoleId());
        String[] newPermissions = permissionMaster.getNewFunctionCode();
        List<String> toCompareList = new ArrayList<>();
        List<String> newPermissionList = Arrays.asList(newPermissions);

        List<String> existingPermissionList = existingPermissions.stream()
            .flatMap(permission -> Arrays.stream(permission.getFunctionCode())) 
            .collect(Collectors.toList());

        for (String existingPerm : existingPermissionList) {
            if (newPermissionList.contains(existingPerm)) {
              for (PermissionMaster permission : existingPermissions) {
                if (Arrays.asList(permission.getFunctionCode())
                    .contains(existingPerm)) {
                  Integer permissionId = permission.getPermissionId();
                  String updateSql = "UPDATE project01.permission SET" + 
                    " update_id = ?, update_date = DEFAULT WHERE role_id = ?" +
                    " AND function_code = ? AND permission_id = ?";
                  Object[] update_mst_params = {
                    roleMaster.getUpdateId(),
                    roleMaster.getRoleId(),
                    existingPerm,
                    permissionId
                  }; 
                  
                  
                  jdbcTemplate.update(updateSql, update_mst_params);
                  break;
                }
              }
          }
          else{
            toCompareList.add(existingPerm);
            continue;
          }
        }
          

        if(!toCompareList.isEmpty()){
          for(String delPerm : toCompareList){
            String updateDelFlagSql = "UPDATE project01.permission SET" +
                " del_flag = 1, update_id = ?, update_date = DEFAULT WHERE" + 
                " role_id = ? AND function_code = ?";
                    jdbcTemplate.update(updateDelFlagSql, 
                    roleMaster.getUpdateId(), 
                    roleMaster.getRoleId(), 
                    delPerm);
          }
        }

        for (String newPerm : newPermissions) {
          if (!existingPermissionList.contains(newPerm)) {
            String insertSql = "INSERT INTO project01.permission " +
                "(role_id, function_code, create_id, update_id, update_date) "
                  + "VALUES (?, ?, ?, ?, DEFAULT)";
                  Object[] insert_mst_params = {
                      roleMaster.getRoleId(),
                      newPerm,
                      roleMaster.getCreateId(),
                      roleMaster.getUpdateId()
                  }; 
                
            jdbcTemplate.update(insertSql, insert_mst_params);
          }
        }

        
      } 
      
    }
    catch(Exception e){
      e.printStackTrace();
    }

  }

}
