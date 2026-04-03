/*
 * UM002Form.java
 * User Registration Form
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.form;

import java.util.List;

import org.springframework.stereotype.Component;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserStatus;


@Component
public class UM002Form {

  private String screenTitle;
  private String associateId;
  private List<PositionMaster> positionList;
  private List<RoleMaster> roleList;
  private List<DepartmentMaster> departmentList;
  private List<SectionMaster> sectionList;
  private List<UserStatus> userStatusList;
  private String errorCode;
  private String errorItem;

  // Getter and Setter


  public String getScreenTitle() {
    return screenTitle;
  }

  public void setScreenTitle(String screenTitle) {
    this.screenTitle = screenTitle;
  }

  public String getAssociateId() {
    return associateId;
  }

  public void setAssociateId(String associateId) {
    this.associateId = associateId;
  }

  public List<PositionMaster> getPositionList() {
    return positionList;
  }

  public void setPositionList(List<PositionMaster> positionList) {
    this.positionList = positionList;
  }

  public List<RoleMaster> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<RoleMaster> roleList) {
    this.roleList = roleList;
  }

  public List<DepartmentMaster> getDepartmentList() {
    return departmentList;
  }

  public void setDepartmentList(List<DepartmentMaster> departmentList) {
    this.departmentList = departmentList;
  }

  public List<SectionMaster> getSectionList() {
    return sectionList;
  }

  public void setSectionList(List<SectionMaster> sectionList) {
    this.sectionList = sectionList;
  }

  public List<UserStatus> getUserStatusList() {
    return userStatusList;
  }

  public void setUserStatusList(List<UserStatus> userStatusList) {
    this.userStatusList = userStatusList;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorItem() {
    return errorItem;
  }

  public void setErrorItem(String errorItem) {
    this.errorItem = errorItem;
  }

}
