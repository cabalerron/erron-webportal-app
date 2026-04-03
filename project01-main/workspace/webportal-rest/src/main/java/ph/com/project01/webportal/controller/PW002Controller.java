/*
 * PW002Controller
 * Controller for updating forgotten password and checking of token
 
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 */

 package ph.com.project01.webportal.controller;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 import ph.com.project01.webportal.model.UsersMaster;
 import ph.com.project01.webportal.service.PW002Service;
 
 @RestController
 @RequestMapping("/public")
 public class PW002Controller {
 
     @Autowired
     private PW002Service pw002Service;
 
     @PostMapping("/reset-pass")
     public ResponseEntity<Map<String, String>> handleResetPassword(
             @RequestParam String token, 
             @RequestParam String newPassword, 
             @RequestParam String confPassword) {
         
         // Check if newPassword is null or empty
         if (newPassword == null || newPassword.trim().isEmpty()) {
             return ResponseEntity.badRequest()
                     .body(Map.of(
                         "errorCode", "E_TSCL_01_0006", 
                         "fieldName", "New Password"));
         }
         // Check if confirmPassword is null or empty
         if (confPassword == null || confPassword.trim().isEmpty()) {
             return ResponseEntity.badRequest()
                     .body(Map.of(
                         "errorCode", "E_TSCL_01_0006", 
                         "fieldName", "Confirm Password"));
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
                         "errorCode", "E_TSCL_01_0002"));
         }
 
         UsersMaster user = pw002Service.findByResetToken(token);
 
         if (pw002Service.isTokenValid(user)) {
             pw002Service.updatePasswordByToken(
                 token, 
                 newPassword);
             return ResponseEntity.ok(Map.of(
                 "errorCode", "I_TSCL_01_0040", 
                 "fieldName", "Password"));
         } else {
             return ResponseEntity.badRequest()
                     .body(Map.of(
                         "errorCode", "E_TSCL_01_0035"));
         }
     }
 
     @GetMapping("/check-token")
     public ResponseEntity<String> checkResetToken(@RequestParam String token) {
         UsersMaster user = pw002Service.findByResetToken(token);
         if (pw002Service.isTokenValid(user)) {
             return ResponseEntity.ok(
                 "Valid Token");
         } else {
             return ResponseEntity.badRequest()
                 .body(
                     "E_TSCL_01_0035");
         }
     }
 }
 