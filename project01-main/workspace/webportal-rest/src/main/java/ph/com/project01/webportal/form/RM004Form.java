/*
 * RM004
 * Role Deletion form
 *
 * v 00.001 - 10/31/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.form;

import java.util.List;
import org.springframework.stereotype.Component;

import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.model.RoleMaster;

@Component
public class RM004Form extends BaseForm {

    private static final long serialVersionUID = 1L;

    private List<RoleMaster> roleDetail;

    private List<PermissionMaster> permissionList;

    private List<FunctionMaster> functionList;

    private String errCode;

    private String errItem;

    private String roleId;

    // ----------------------------------------
    // Getters and Setters
    // ----------------------------------------

    public String getErrItem() {
        return errItem;
    }

    public void setErrItem(String errItem) {
        this.errItem = errItem;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public List<RoleMaster> getRoleDetail() {
        return roleDetail; 
    }

    public void setRoleDetail(List<RoleMaster> roleDetail) {
        this.roleDetail = roleDetail;
    }

    public List<PermissionMaster> getPermissionList() {
        return permissionList; 
    }

    public void setPermissionList(List<PermissionMaster> permissionList) {
        this.permissionList = permissionList;
    }

    public List<FunctionMaster> getFunctionList() {
        return functionList; 
    }

    public void setFunctionList(List<FunctionMaster> functionList) {
        this.functionList = functionList;
    }

    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
