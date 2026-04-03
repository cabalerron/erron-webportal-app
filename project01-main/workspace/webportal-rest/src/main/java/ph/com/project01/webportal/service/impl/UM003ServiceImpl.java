/*
 * UM003ServiceImpl.java
 * User Edit Service Implementation
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
import ph.com.project01.webportal.repository.UM003Repository;
import ph.com.project01.webportal.service.UM003Service;

@Service
public class UM003ServiceImpl implements UM003Service{

  private final UM002Repository um002Repository;
  private final UM003Repository um003Repository; 

  public UM003ServiceImpl(UM002Repository um002RepositoryImpl, 
  UM003Repository um003RepositoryImpl) {
    this.um002Repository = um002RepositoryImpl;
    this.um003Repository = um003RepositoryImpl;
  }

  @Override
  public String getScreenTitle() {
    StringBuilder screenTitle = new StringBuilder();
    String tier1 = um002Repository.getFunctionName("11").split(" ")[0];
    String tier2 = um002Repository.getFunctionName("112");
    screenTitle.append(tier1).append(" ").append(tier2);
    return screenTitle.toString();
  }

  @Override
  public void updateUser(UserMaster userMaster, 
  UserInfo userInfo, 
  String loggedInUsername) {
    
    UserMaster userMasterOld = um002Repository
    .getUserMaster(userMaster.getUserId());
    UserInfo userInfoOld = um002Repository
    .getUserInfo(userMaster.getUserId());
    
    // if user image is not updated, set the old image
    if(userInfo.getImgPath() == null) {
      userInfo.setImgPath(userInfoOld.getImgPath());
    }

    userMaster.setCreateId(userMasterOld.getCreateId());
    userMaster.setCreateDate(userMasterOld.getCreateDate());
    userInfo.setCreateId(userInfoOld.getCreateId());
    userInfo.setCreateDate(userInfoOld.getCreateDate());

    um003Repository.updateUser(userMaster, userInfo, loggedInUsername);
  }

  @Override
  public void unlockUser(int userId, String loggedInUsername) {
    um003Repository.unlockUser(userId, loggedInUsername);
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
  public String findExistingRecords(String associateId, 
  String email, 
  String username, 
  int userId) {
    UserMaster userMaster = um002Repository.getUserMasterByAssociateId(associateId);
    if(userMaster != null && userMaster.getUserId() != userId) {
      return "Associate ID";
    }
    else if (um002Repository.getUserMasterByUsername(username) != null 
    && um002Repository.getUserMasterByUsername(username).getUserId() != userId){
      return "Username";
    }
    else if (um002Repository.getUserMasterByEmail(email) != null 
    && um002Repository.getUserMasterByEmail(email).getUserId() != userId) {
      return "Email";
    }
    return null;
  }
}
