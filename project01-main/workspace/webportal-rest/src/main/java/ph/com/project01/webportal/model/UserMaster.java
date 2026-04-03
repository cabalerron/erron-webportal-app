package ph.com.project01.webportal.model;

import java.sql.Timestamp ;

import org.springframework.lang.Nullable;


public class UserMaster {

  private int userId;
 
  private String accountId;

  private String associateId;

  private String password;

  @Nullable
  private int positionId;

  @Nullable
  private int departmentId;

  @Nullable
  private int sectionId;

  @Nullable
  private int statusId;

  private int roleId;

  private String mailaddress;

  private int invalidTry;

  private Timestamp lastLoginDate;

  private int lockFlag;

  private String resetToken;

  private Timestamp tokenExp;

  private int delFlag;

  private String createId;

  private Timestamp createDate;

  private String updateId;

  private Timestamp updateDate;

  // Getter and Setter

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAssociateId() {
    return associateId;
  }

  public void setAssociateId(String associateId) {
    this.associateId = associateId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
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

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getMailaddress() {
    return mailaddress;
  }

  public void setMailaddress(String mailaddress) {
    this.mailaddress = mailaddress;
  }

  public int getInvalidTry() {
    return invalidTry;
  }

  public void setInvalidTry(int invalidTry) {
    this.invalidTry = invalidTry;
  }

  public Timestamp getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(Timestamp lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public int getLockFlag() {
    return lockFlag;
  }

  public void setLockFlag(int lockFlag) {
    this.lockFlag = lockFlag;
  }

  public String getResetToken() {
    return resetToken;
  }

  public void setResetToken(String resetToken) {
    this.resetToken = resetToken;
  }

  public Timestamp getTokenExp() {
    return tokenExp;
  }

  public void setTokenExp(Timestamp tokenExp) {
    this.tokenExp = tokenExp;
  }

  public int getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(int delFlag) {
    this.delFlag = delFlag;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }

}
