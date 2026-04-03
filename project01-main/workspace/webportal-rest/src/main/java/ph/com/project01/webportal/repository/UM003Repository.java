/*
 * UM003Repository.java
 * User Edit Repository Interface
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository;

import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;

public interface UM003Repository  {

  /**
   * Save User Master
   * @param userId
   */
  void updateUser(UserMaster userMaster, 
  UserInfo userInfo, 
  String loggedInUserName);

  /**
   * Unlock User
   * @param userId
   */
  void unlockUser(int userId, String loggedInUserName);


}
