package ph.com.project01.webportal.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_mst", schema = "project01")
public class UsersMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "account_id", length = 50, nullable = false)
    private String accountId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "mailaddress", length = 100)
    private String mailAddress;

    @Column(name = "invalid_try")
    private Integer invalidTry;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "lock_flag")
    private Integer lockFlag;

    @Column(name = "reset_token", length = 255)
    private String resetToken;

    @Column(name = "token_exp")
    private LocalDateTime tokenExp;

    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

    @Column(name = "create_id", length = 50)
    private String createId;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_id", length = 50)
    private String updateId;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Integer getInvalidTry() {
        return invalidTry;
    }

    public void setInvalidTry(Integer invalidTry) {
        this.invalidTry = invalidTry;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExp() {
        return tokenExp;
    }

    public void setTokenExp(LocalDateTime tokenExp) {
        this.tokenExp = tokenExp;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
