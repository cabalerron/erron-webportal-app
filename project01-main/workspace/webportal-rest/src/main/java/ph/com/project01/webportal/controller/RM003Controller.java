/*
 * RM003
 * Used to handle requests related to fetching and edit data in database.
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.controller;
import ph.com.project01.webportal.form.RM003Form;
import ph.com.project01.webportal.model.FunctionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.PermissionMaster;
import ph.com.project01.webportal.service.RM003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RM003Controller {

    @Autowired
    private RM003Service rm003Service;

    @PostMapping("/initEdit")
    public ResponseEntity<RM003Form> init(
        @RequestParam("roleId") Integer roleId
    ) {
        RM003Form rm003Form = new RM003Form();

        List<RoleMaster> roleDetails = rm003Service.getRoleDetail(roleId);
        List<PermissionMaster> permissionList = 
            rm003Service.getPermissionList(roleId);
        List<FunctionMaster> functionList = rm003Service.getFunctionList();

        rm003Form.setRoleDetail(roleDetails);
        rm003Form.setPermissionList(permissionList);
        rm003Form.setFunctionList(functionList);

        return ResponseEntity.ok(rm003Form);
    }

    @PostMapping("/editRole")
    public ResponseEntity<RM003Form> deleteRole(
        @RequestParam("loggedInUsername") String loggedInUsername,
        @RequestParam("roleId") Integer roleId,
        @RequestParam("roleName") String roleName,
        @RequestParam("roleShName") String roleShName,
        @RequestParam("permissions") List<String> permissions
    ) {
        RM003Form rm003Form = new RM003Form();
        try{
            RoleMaster roleMaster = new RoleMaster();
                roleMaster.setRoleId(roleId);
                roleMaster.setRoleName(roleName);
                roleMaster.setRoleShName(roleShName);

                String[] functionCode = permissions.toArray(new String[0]);

                PermissionMaster permissionMaster = new PermissionMaster();
                permissionMaster.setRoleId(roleId);
                permissionMaster.setNewFunctionCode(functionCode);

            rm003Service.editRole(loggedInUsername, 
                roleMaster, permissionMaster);
        } catch (Exception e) {
            rm003Form.setErrCode("E_TSCL_01_0033");
            rm003Form.setErrItem("role");
        }
        

        return ResponseEntity.ok(rm003Form);
    }

}
