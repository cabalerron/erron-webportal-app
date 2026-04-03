/*
 * UM005Controller
 * Controller for updating password
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ph.com.project01.webportal.service.UM005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
public class UM005Controller {

    @Autowired
    private UM005Service um005Service;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
        @RequestParam String accountId,
        @RequestParam String oldPassword,
        @RequestParam String newPassword,
        @RequestParam String confPassword) {
        
        // Check if input is null or empty
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0006",
                         "fieldName", "Old Password"));
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0006",
                        "fieldName", "New Password"));
        }
        if (confPassword == null || confPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0006", 
                        "fieldName", "Confirm Password"));
        }

        // Check if old password is correct
        if (!um005Service.isOldPasswordCorrect(accountId, oldPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "errorCode", "I_TSCL_01_0031"));
        }

        // Check if newPassword matches confirmPassword
        if (!newPassword.equals(confPassword)) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0005"));
        }

        // Password length validation
        if (newPassword.length() < 8) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0004"));
        }

        String passwordPattern = 
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0002") );
        }

        // Password Update
        um005Service.updatePassword(
            accountId, 
            newPassword);
            return ResponseEntity.ok(Map.of(
                "errorCode", "I_TSCL_01_0040", 
                "fieldName", "Password"));
    }
}
