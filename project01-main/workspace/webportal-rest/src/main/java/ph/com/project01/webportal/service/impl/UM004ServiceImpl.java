/*
 * UM004ServiceImpl.java
 * User Delete Service Implementation
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.repository.UM002Repository;
import ph.com.project01.webportal.repository.UM004Repository;
import ph.com.project01.webportal.service.UM004Service;

@Service
public class UM004ServiceImpl implements UM004Service{

  private final UM002Repository um002Repository; 
  private final UM004Repository um004Repository;

  public UM004ServiceImpl(UM002Repository um002RepositoryImpl, 
  UM004Repository um004RepositoryImpl) {
    this.um002Repository = um002RepositoryImpl;
    this.um004Repository = um004RepositoryImpl;

  }

  @Override
  public String getScreenTitle() {
    StringBuilder screenTitle = new StringBuilder();
    String tier1 = um002Repository.getFunctionName("11").split(" ")[0];
    String tier2 = um002Repository.getFunctionName("113");
    screenTitle.append(tier1).append(" ").append(tier2);
    return screenTitle.toString();
  }

  @Override
  public UserMaster getUserMaster(int userId) {
    return um002Repository.getUserMaster(userId);
  }

  @Override
  public UserInfo getUserInfo(int userId) {
    return um002Repository.getUserInfo(userId);
  }

  @Override
  public List<RoleMaster> getAllRole() {
    return um002Repository.getAllRole();
  }

  @Override
  public List<PositionMaster> getAllPosition() {
    return um002Repository.getAllPosition();
  }

  @Override
  public List<DepartmentMaster> getAllDepartment() {
    return um002Repository.getAllDepartment();
  }

  @Override
  public List<SectionMaster> getAllSection() {
    return um002Repository.getAllSection();
  }

  @Override
  public List<UserStatus> getAllUserStatus() {
    return um002Repository.getAllUserStatus();
  }

  @Override
  public void deleteUser(int userId, String loggedInUsername) {
    int result = um004Repository.deleteUser(userId, loggedInUsername);
    if (result == 0) {
      throw new RuntimeException("User not found");
    }
    if (result == -1) {
      throw new RuntimeException("Error in deleting user");
    }
  }
  
}
