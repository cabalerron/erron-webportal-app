/*
 * RM002
 * Role Registration form
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.form;

import java.util.List;
import org.springframework.stereotype.Component;

import ph.com.project01.webportal.model.FunctionMaster;

@Component
public class RM002Form extends BaseForm {

    private static final long serialVersionUID = 1L;

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
