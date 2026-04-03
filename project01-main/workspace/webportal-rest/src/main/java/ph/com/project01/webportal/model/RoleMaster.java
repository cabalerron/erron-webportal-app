package ph.com.project01.webportal.model;

import java.sql.Timestamp ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_mst")
public class RoleMaster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private int roleId;

  @Column(name = "role_name", length = 100, nullable = false)
  private String roleName;

  @Column(name = "role_sh_name", length = 50, nullable = false)
  private String roleShName;

  @Column(name = "del_flag", nullable = false)
  private int delFlag;
  
  @Column(name = "create_id", length = 50)
  private String createId;

  @Column(name = "create_date")
  private Timestamp createDate;

  @Column(name = "update_id", length = 50)
  private String updateId;

  @Column(name = "update_date")
  private Timestamp updateDate;

  // Getters and Setters
  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleShName() {
    return roleShName;
  }

  public void setRoleShName(String roleShName) {
    this.roleShName = roleShName;
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
