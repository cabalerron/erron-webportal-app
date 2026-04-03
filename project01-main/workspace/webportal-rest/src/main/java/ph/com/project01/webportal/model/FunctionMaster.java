/*
 * RM002
 * Used to represent the function table in the database.
 * 
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "function_mst", schema = "project01")
public class FunctionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "function_id")
    private Integer functionId;

    @Column(name = "function_code", length = 50, nullable = false)
    private String functionCode;

    @Column(name = "function_name", length = 50, nullable = false)
    private String functionName;

    @Column(name = "module_code")
    private String moduleCode;

    @Column(name = "tier", nullable = false)
    private Integer tier;

    @Column(name = "icon_id")
    private Integer iconId;

    @Column(name = "iconUrl")
    private String iconUrl;

    @Column(name = "display_index")
    private Integer displayIndex;

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
    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
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
