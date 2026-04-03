/*
 * RM002, RM003, RM004
 * Used to represent the permission table in the database.
 *
 * v 00.001 - 10/31/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.model;

import java.time.LocalDateTime;
import ph.com.project01.webportal.common.PortalConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permission", schema = PortalConstants.SCHEMA)
public class PermissionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "function_code")
    private String[] functionCode;

    @Column(name = "role_id")
    private Integer roleId;

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
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String[] getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String[] functionCode) {
        this.functionCode = functionCode;
    }

    public String[] getNewFunctionCode() {
        return functionCode;
    }

    public void setNewFunctionCode(String[] functionCode) {
        this.functionCode = functionCode;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
