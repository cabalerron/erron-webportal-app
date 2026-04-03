/*
 * PW001Controller
 * Controller for email input checking
 * 
 * v 00.001 - 10/22/2024
 *
 * PIC: emonteverde
 */

 package ph.com.project01.webportal.controller;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 
 import ph.com.project01.webportal.service.PW001Service;
 
 import java.util.Map;
 import java.util.regex.Pattern;
 
 @RestController
 @RequestMapping("/public")
 public class PW001Controller {
 
     @Autowired
     private PW001Service pw001Service;
 
     private static final Pattern EMAIL_PATTERN = Pattern.compile(
             "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
             Pattern.CASE_INSENSITIVE);
 
     @PostMapping("/send-link")
     public ResponseEntity<Map<String, String>> handleForgotPassword(
             @RequestParam String email) {
         // Check if email is null or empty
         if (email == null || email.trim().isEmpty()) {
             return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                 .body(Map.of(
                     "messageCode", "E_TSCL_01_0006", 
                     "fieldName", "Email"));
         }
 
         // Validate email format
         if (!EMAIL_PATTERN.matcher(email).matches()) {
             return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                 .body(Map.of(
                     "messageCode", "E_TSCL_01_0016",
                     "fieldName", "Email"));
         }
 
         // Check if email is registered
         if (pw001Service.isEmailRegistered(email)) {
             pw001Service.createPasswordResetToken(email);
             return ResponseEntity
                 .ok(Map.of(
                     "messageCode",
                     "I_TSCL_01_0027"));
         } else {
             return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                 .body(Map.of(
                     "messageCode", "E_TSCL_01_0026"));
         }
     }
 }
 