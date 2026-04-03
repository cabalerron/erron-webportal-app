/*
 * UM003Service.java
 * User Edit Service Interface
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service;

import java.util.List;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;

public interface UM003Service {

  /**
   * Get Function Name
   * 
   * @return String
   */
  String getScreenTitle();

  /**
   * Get User Master by user id
   * @param userId
   * @return UserMaster
   */
  UserMaster getUserMaster(int userId);

  /**
   * Get User Info by user id
   * @param userId
   * @return UserInfo
   */
  UserInfo getUserInfo(int userId);

  /**
   * Save User Master
   * @param userMaster
   * @param userInfo
   * @param userId
   */
  public void updateUser(UserMaster userMaster, 
  UserInfo userInfo, 
  String loggedInUserName);

  /**
   * Unlock User
   * @param userId
   */
  public void unlockUser(int userId, String loggedInUserName);

  /**
   * Get all role
   * @return List<Role>
   */
  List<RoleMaster> getAllRole();

  /**
   * Get all position
   * @return List<Position>
   */
  List<PositionMaster> getAllPosition();

  /**
   * Get all department
   * @return List<Department>
   */
  List<DepartmentMaster> getAllDepartment();

  /**
   * Get all section
   * @return List<Section>
   */
  List<SectionMaster> getAllSection();

  /**
   * Get all user status
   * @return List<UserStatus>
   */
  List<UserStatus> getAllUserStatus();

  /**
   * Find if the user already exists
   * @param associateId
   * @param email
   * @param username
   * @return
   */
  String findExistingRecords(String associateId, String email, String username, int userId);

}
