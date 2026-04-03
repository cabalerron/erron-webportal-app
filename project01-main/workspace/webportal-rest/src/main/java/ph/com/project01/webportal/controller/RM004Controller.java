/*
 * RM004
 * Used to handle requests related to fetching and deleting data in database.
 *
 * v 00.001 - 10/31/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.controller;
import ph.com.project01.webportal.form.RM004Form;
import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.service.RM004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RM004Controller {

    @Autowired
    private RM004Service rm004Service;

    @PostMapping("/initDelete")
    public ResponseEntity<RM004Form> init(
        @RequestParam("roleId") Integer roleId
    ) {
        RM004Form rm004Form = new RM004Form();

        List<RoleMaster> roleDetails = rm004Service.getRoleDetail(roleId);
        List<PermissionMaster> permissionList = 
            rm004Service.getPermissionList(roleId);
        List<FunctionMaster> functionList = rm004Service.getFunctionList();

        rm004Form.setRoleDetail(roleDetails);
        rm004Form.setPermissionList(permissionList);
        rm004Form.setFunctionList(functionList);

        return ResponseEntity.ok(rm004Form);
    }

    @PostMapping("/deleteRole")
    public ResponseEntity<RM004Form> deleteRole(
        @RequestParam("loggedInUsername") String loggedInUsername,
        @RequestParam("roleId") Integer roleId
    ) {
        RM004Form rm004Form = new RM004Form();
        try{
            rm004Service.deleteRole(roleId, loggedInUsername);
        } catch (Exception e) {
            rm004Form.setErrCode("E_TSCL_01_0033");
            rm004Form.setErrItem("role");
        }
        

        return ResponseEntity.ok(rm004Form);
    }

}
