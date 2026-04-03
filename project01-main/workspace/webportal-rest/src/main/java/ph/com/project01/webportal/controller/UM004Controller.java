/*
 * UM004Controller.java
 * User Delete Controller
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

import ph.com.project01.webportal.form.UM004Form;
import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.service.UM004Service;

/**
 * User Delete Controller
 */
@RestController
public class UM004Controller {

  private final UM004Service um004Service;

  private UM004Form um004Form;

  public UM004Controller(UM004Service um004Service, UM004Form um004Form) {
    this.um004Service = um004Service;
    this.um004Form = um004Form;
  }

  @PostMapping("/dispUM004")
  public ResponseEntity<UM004Form> dispUM004(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
      @RequestParam("userId") String userId) {

    String screenTitle = um004Service.getScreenTitle();
    List<PositionMaster> positionList = um004Service.getAllPosition();
    List<RoleMaster> roleList = um004Service.getAllRole();
    List<DepartmentMaster> departmentList = um004Service.getAllDepartment();
    List<SectionMaster> sectionList = um004Service.getAllSection();
    List<UserStatus> userStatusList = um004Service.getAllUserStatus();

    UserMaster userMaster = um004Service.getUserMaster(Integer.parseInt(userId));
    UserInfo userInfo = um004Service.getUserInfo(Integer.parseInt(userId));
    um004Form.setScreenTitle(screenTitle);
    um004Form.setPositionList(positionList);
    um004Form.setRoleList(roleList);
    um004Form.setDepartmentList(departmentList);
    um004Form.setSectionList(sectionList);
    um004Form.setUserStatusList(userStatusList);

    // Set user master and user info
    um004Form.setImgUserPhoto(userInfo.getImgPath());
    um004Form.setFirstName(userInfo.getFirstName());
    um004Form.setMiddleName(userInfo.getMiddleName());
    um004Form.setLastName(userInfo.getLastName());
    um004Form.setAssociateId(userMaster.getAssociateId());
    um004Form.setUsername(userMaster.getAccountId());
    um004Form.setEmail(userMaster.getMailaddress());
    um004Form.setStatusId(userMaster.getStatusId());
    um004Form.setPositionId(userMaster.getPositionId());
    um004Form.setRoleId(userMaster.getRoleId());
    um004Form.setDepartmentId(userMaster.getDepartmentId());
    um004Form.setSectionId(userMaster.getSectionId());
    um004Form.setDelFlag(userMaster.getDelFlag());

    return ResponseEntity.ok(um004Form);

  }

  @PostMapping("/deleteUser")
  public ResponseEntity<UM004Form> deleteUser(
      @RequestParam("loggedInUsername") String loggedInUsername,
      @RequestParam("loggedInUserRole") String loggedInUserRole,
      @RequestParam("userId") String userId) {

    try {
      um004Service.deleteUser(Integer.parseInt(userId), loggedInUsername);
    } catch (Exception e) {
      um004Form.setErrorCode("E_TSCL_01_0033");
      um004Form.setErrorItem("user");
    }
    return ResponseEntity.ok(um004Form);

  }
}
