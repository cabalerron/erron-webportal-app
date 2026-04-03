/*
 * UM002Repository.java
 * User Master Repository Interface
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository;

import java.util.List;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;

public interface UM002Repository  {

  /**
   * Get Function Name
   * @return String
   */
  String getFunctionName(String functionCode);

  /**
   * Save User Master
   * @param
   * 
   * @return Numbers of affected rows count, -1 if error
   * 
   * @throws java.lang.Exception
   */
  int insertUser(UserMaster userMaster, UserInfo userInfo);

  /**
   * Get next associate id
   * @return String
   */
  String getNextAssociateId();

  /**
   * Get User Master by user id
   * @param userId
   * @return UserMaster
   */
  UserMaster getUserMaster(int userId); 

  /**
   * Get User Master by associate id
   * @param associateId
   * @return UserMaster
   */
  UserMaster getUserMasterByAssociateId(String associateId);

  /**
   * Get User Master by email
   * @param mail
   * @return UserMaster
   */
  UserMaster getUserMasterByEmail(String mail);

  /**
   * Get User Master by username
   * @param username
   * @return UserMaster
   */
  UserMaster getUserMasterByUsername(String username);

  /**
   * Get User Info by user id
   * @param userId
   * @return UserInfo
   */
  UserInfo getUserInfo(int userId);

  /**
   * Get all role
   * @return List of RoleMaster
   */
  List<RoleMaster> getAllRole();

  /**
   * Get all position
   * @return List of PositionMaster
   */
  List<PositionMaster> getAllPosition();

  /**
   * Get all department
   * @return List of DepartmentMaster
   */
  List<DepartmentMaster> getAllDepartment();

  /**
   * Get all section
   * @return List of SectionMaster
   */
  List<SectionMaster> getAllSection();

  /**
   * Get all user status
   * @return List of UserStatus
   */
  List<UserStatus> getAllUserStatus();
}
