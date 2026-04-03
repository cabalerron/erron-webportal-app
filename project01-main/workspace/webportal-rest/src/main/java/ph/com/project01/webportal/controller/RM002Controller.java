/*
 * RM002
 * Used to handle requests related to fetching and inserting data in database.
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.controller;
import ph.com.project01.webportal.form.RM002Form;
import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.service.RM002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RM002Controller {

    @Autowired
    private RM002Service rm002Service;

    @GetMapping("/initRegis")
    public ResponseEntity<RM002Form> init() {
        RM002Form rm002Form = new RM002Form();

        List<FunctionMaster> functionList = rm002Service.getFunctionList();
        String nextRoleId = rm002Service.getNextRoleId();

        rm002Form.setFunctionList(functionList);
        rm002Form.setRoleId(nextRoleId);

        return ResponseEntity.ok(rm002Form);
    }

    @PostMapping("/registerRole")
    public ResponseEntity<?> registerRole(
        
        @RequestParam("loggedInUsername") String loggedInUsername,
        @RequestParam("loggedInUserRole") String loggedInUserRole,
        @RequestParam("roleId") int roleId,
        @RequestParam("roleName") String roleName,
        @RequestParam("roleShName") String roleShName,
        @RequestParam("permissions") List<String> permissions) {
    
            RM002Form rm002Form = new RM002Form();
            String errorCode = "";
            try {
                RoleMaster roleMaster = new RoleMaster();
                roleMaster.setRoleId(roleId);
                roleMaster.setRoleName(roleName);
                roleMaster.setRoleShName(roleShName);
                
                String[] functionCode = permissions.toArray(new String[0]);

                PermissionMaster permissionMaster = new PermissionMaster();
                permissionMaster.setRoleId(roleId);
                permissionMaster.setFunctionCode(functionCode);

                String existRoleMessage = 
                        rm002Service.findExistingRecords(roleName);
                if (existRoleMessage != null) {
                    rm002Form.setErrCode("E_TSCL_01_0009");
                    rm002Form.setErrItem(existRoleMessage);
                }

                rm002Service.registerRole(roleMaster, permissionMaster, 
                        loggedInUsername);

            } catch (Exception e) {
                errorCode = "E_TSCL_01_0021";
                return ResponseEntity.badRequest().body(errorCode);
            }

        return ResponseEntity.ok(rm002Form);
    }
}
