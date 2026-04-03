/*
 * UM002Controller.java
 * User Registration controller
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ph.com.project01.webportal.form.UM002Form;
import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.service.FileService;
import ph.com.project01.webportal.service.UM002Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Registration controller
 */
@RestController()
public class UM002Controller {

  private final UM002Service um002Service;

  private final UM002Form um002Form;

  private final FileService fileService;

  public UM002Controller(UM002Service um002Service, UM002Form um002Form, FileService fileService) {
    this.um002Service = um002Service;
    this.um002Form = um002Form;
    this.fileService = fileService;
  }

  @PostMapping("/dispUM002")
  public ResponseEntity<UM002Form> dispUM002(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole) {

    String screenTitle = um002Service.getScreenTitle();
    List<PositionMaster> positionList = um002Service.getAllPosition();
    List<RoleMaster> roleList = um002Service.getAllRole();
    List<DepartmentMaster> departmentList = um002Service.getAllDepartment();
    List<SectionMaster> sectionList = um002Service.getAllSection();
    List<UserStatus> userStatusList = um002Service.getAllUserStatus();
    String nextAssociateId = um002Service.getNextAssociateId();

    um002Form.setScreenTitle(screenTitle);
    um002Form.setAssociateId(nextAssociateId);
    um002Form.setPositionList(positionList);
    um002Form.setRoleList(roleList);
    um002Form.setDepartmentList(departmentList);
    um002Form.setSectionList(sectionList);
    um002Form.setUserStatusList(userStatusList);
    return ResponseEntity.ok(um002Form);
  }

  @PostMapping("/registerUser")
  public ResponseEntity<UM002Form> registerUser(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
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
      UM002Form um002Form = new UM002Form();

    try {
      
      String imgPath = fileService.saveFile(file);
      userInfo.setImgPath(imgPath);
      userInfo.setFirstName(firstName);
      userInfo.setMiddleName(middleName);
      userInfo.setLastName(lastName);

      userMaster.setAssociateId(associateId);
      userMaster.setRoleId(Integer.parseInt(roleId));
      userMaster.setMailaddress(email);
      userMaster.setAccountId(username);

      userMaster.setPositionId(positionId.isEmpty() ? 0 : Integer.parseInt(positionId));
      userMaster.setDepartmentId(departmentId.isEmpty() ? 0 : Integer.parseInt(departmentId));
      userMaster.setSectionId(sectionId.isEmpty() ? 0 : Integer.parseInt(sectionId));
      userMaster.setStatusId(userStatusId.isEmpty() ? 0 : Integer.parseInt(userStatusId));

      // check if unique ids' exists
      String existIdMessage = um002Service.findExistingRecords(associateId, email, username);
      if (existIdMessage != null) {
        um002Form.setErrorCode("E_TSCL_01_0009");
        um002Form.setErrorItem(existIdMessage);
      }
      else {
        um002Service.registerUser(userMaster, userInfo, loggedInUsername);
      }

    } catch (Exception e) {
      um002Form.setErrorCode("E_TSCL_01_021");
    }

    return ResponseEntity.ok(um002Form);
  }

}
