/*
 * UM003Controller.java
 * User Edit Controller
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.form.UM003Form;
import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.service.FileService;
import ph.com.project01.webportal.service.UM003Service;

/**
 * User Edit Controller
 */
@RestController
public class UM003Controller {

  private final UM003Service um003Service;

  private final UM003Form um003Form;

  private final FileService fileService;

  public UM003Controller(UM003Service um003Service, UM003Form um003Form, FileService fileService) {
    this.um003Service = um003Service;
    this.um003Form = um003Form;
    this.fileService = fileService;
  }

  @PostMapping("/dispUM003")
  public ResponseEntity<UM003Form> dispUM003(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
      @RequestParam("userId") String userId ){

    String screenTitle = um003Service.getScreenTitle();
    List<PositionMaster> positionList = um003Service.getAllPosition();
    List<RoleMaster> roleList = um003Service.getAllRole();
    List<DepartmentMaster> departmentList = um003Service.getAllDepartment();
    List<SectionMaster> sectionList = um003Service.getAllSection();
    List<UserStatus> userStatusList = um003Service.getAllUserStatus();

    UserMaster userMaster = um003Service.getUserMaster(Integer.parseInt(userId));
    UserInfo userInfo = um003Service.getUserInfo(Integer.parseInt(userId));
    um003Form.setScreenTitle(screenTitle);
    um003Form.setPositionList(positionList);
    um003Form.setRoleList(roleList);
    um003Form.setDepartmentList(departmentList);
    um003Form.setSectionList(sectionList);
    um003Form.setUserStatusList(userStatusList);

    // Set user master and user info
    um003Form.setFirstName(userInfo.getFirstName());
    um003Form.setMiddleName(userInfo.getMiddleName());
    um003Form.setLastName(userInfo.getLastName());
    um003Form.setImgUserPhoto(userInfo.getImgPath());
    um003Form.setAssociateId(userMaster.getAssociateId());
    um003Form.setUsername(userMaster.getAccountId());
    um003Form.setEmail(userMaster.getMailaddress());
    um003Form.setStatusId(userMaster.getStatusId());
    um003Form.setPositionId(userMaster.getPositionId());
    um003Form.setRoleId(userMaster.getRoleId());
    um003Form.setDepartmentId(userMaster.getDepartmentId());
    um003Form.setSectionId(userMaster.getSectionId());
    um003Form.setLockFlag(userMaster.getLockFlag());

    return ResponseEntity.ok(um003Form);

  }

  @PostMapping("/updateUser")
  public ResponseEntity<UM003Form> updateUser(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
      @RequestParam("userId") String userId,
      @RequestParam("associateId") String associateId,
      @RequestParam(value = "imageFile", required = false) MultipartFile file,
      @RequestParam("firstName") String firstName,
      @RequestParam(value = "middleName", required = false) String middleName,
      @RequestParam("lastName") String lastName,
      @RequestParam(value = "positionId", required = false) String positionId,
      @RequestParam("roleId") String roleId,
      @RequestParam("email") String email,
      @RequestParam(value = "departmentId", required = false) String departmentId,
      @RequestParam(value = "sectionId", required = false) String sectionId,
      @RequestParam("username") String username,
      @RequestParam(value = "userStatusId", required = false) String userStatusId) {

    UserMaster userMaster = new UserMaster();
    UserInfo userInfo = new UserInfo();
    UM003Form um003Form = new UM003Form();

    try{
      
      userInfo.setUserId(Integer.parseInt(userId));
      userMaster.setUserId(Integer.parseInt(userId));

      if (loggedInUserRole.equals(PortalConstants.ADMIN_ROLE)) {

        userInfo.setFirstName(firstName);
        userInfo.setMiddleName(middleName);
        userInfo.setLastName(lastName);
        userInfo.setUpdateId(loggedInUsername);
        userMaster.setAssociateId(associateId);
        userMaster.setAccountId(username);
        userMaster.setMailaddress(email);
        userMaster.setStatusId(Integer.parseInt(userStatusId));
        userMaster.setPositionId(Integer.parseInt(positionId));
        userMaster.setRoleId(Integer.parseInt(roleId));
        userMaster.setDepartmentId(Integer.parseInt(departmentId));
        userMaster.setSectionId(Integer.parseInt(sectionId));
        userMaster.setUpdateId(loggedInUsername);

      }
      else{
        userMaster = um003Service.getUserMaster(Integer.parseInt(userId));
        userInfo = um003Service.getUserInfo(Integer.parseInt(userId));
      }

      if (file != null) {
        String imgPath = fileService.saveFile(file);
        userInfo.setImgPath(imgPath);
      }

      String existIdMessage = um003Service.findExistingRecords(associateId, email, username, Integer.parseInt(userId));
      if (existIdMessage != null) {
        um003Form.setErrorCode("E_TSCL_01_0009");
        um003Form.setErrorItem(existIdMessage);
      }
      else {
        um003Service.updateUser(userMaster, userInfo, loggedInUsername);
      }

    } catch (Exception e) {
      um003Form.setErrorCode("E_TSCL_01_021");
    }

    return ResponseEntity.ok(um003Form);
  }

  @PostMapping("/unlockUser")
  public ResponseEntity<UM003Form> unlockUser(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
      @RequestParam("userId") String userId) {

    try {
      um003Service.unlockUser(Integer.parseInt(userId), loggedInUsername);
    } catch (Exception e) {
      um003Form.setErrorCode("E_TSCL_01_0021");
    }

    return ResponseEntity.ok(um003Form);
  }

}
