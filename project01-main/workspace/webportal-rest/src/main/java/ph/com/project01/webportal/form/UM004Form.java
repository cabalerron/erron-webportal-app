/*
 * UM004Form.java
 * User Delete Form
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
public class UM004Form {

  private String screenTitle;
  private String firstName;
  private String middleName;
  private String lastName;
  private String associateId;
  private String username;
  private String email;
  private int statusId;
  private int positionId;
  private int roleId;
  private int departmentId;
  private int sectionId;
  private String imgUserPhoto;
  private int delFlag;
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

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAssociateId() {
    return associateId;
  }

  public void setAssociateId(String associateId) {
    this.associateId = associateId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public int getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  public int getSectionId() {
    return sectionId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public String getImgUserPhoto() {
    return imgUserPhoto;
  }

  public void setImgUserPhoto(String imgUserPhoto) {
    this.imgUserPhoto = imgUserPhoto;
  }

  public int getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(int delFlag) {
    this.delFlag = delFlag;
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
