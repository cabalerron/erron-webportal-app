/*
 * LGN001Controller
 * Controller for login, logout, and authentication status check
 * 
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
*/

package ph.com.project01.webportal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ph.com.project01.webportal.model.UsersMaster;
import ph.com.project01.webportal.service.LGN001Service;
import ph.com.project01.webportal.common.SessionDetailsUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class LGN001Controller {

    @Autowired
    private LGN001Service userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestParam String accountId,
        @RequestParam String password,
        HttpSession session) {

        // Input validation
        if (accountId == null || accountId.trim().isEmpty()) {
            return ResponseEntity.badRequest()
            .body(Map.of(
                "errorCode", "E_TSCL_01_0006", 
                "fieldName", "Account ID"));
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest()
            .body(Map.of(
                "errorCode", "E_TSCL_01_0006", 
                "fieldName", "Password"));
        }

        // Range validation
        if (accountId.length() > 10) {
            return ResponseEntity.badRequest()
            .body(Map.of(
                "errorCode", "E_TSCL_01_0016", 
                "fieldName", "Account ID"));
        }
        if (password.length() > 15) {
            return ResponseEntity.badRequest()
            .body(Map.of(
                "errorCode", "E_TSCL_01_0016",
                 "fieldName", "Password"));
        }

        try {
            // Check if user is present
            UsersMaster user = userService.findByAccountid(accountId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "errorCode", "E_TSCL_01_0001"));
            }

            // Check if account is deleted
            if (user.getDelFlag() == 1) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                    "errorCode", "E_TSCL_01_0002"));
            }

            // Check if account is locked
            if (user.getLockFlag() == 1) {
                return ResponseEntity.status(HttpStatus.LOCKED)
                .body(Map.of(
                    "errorCode", "E_TSCL_01_0003"));
            }

            // Authenticate user
            if (userService.authenticate(accountId, password)) {

                // Set authentication in the session
                UserDetails userDetails = userService
                    .loadUserByUsername(accountId);
                Authentication authentication = new 
                    UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(), 
                    userDetails.getAuthorities());
                
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(authentication);
                session.setAttribute(
                    HttpSessionSecurityContextRepository
                    .SPRING_SECURITY_CONTEXT_KEY, sc);

                // Return user details
                String name = SessionDetailsUtil.getAccountId();
                String role = SessionDetailsUtil.getRole();
                return ResponseEntity.ok(Map.of(
                    "accountId", name, "roleId", role));

            } else {
                // Handle failed authentication
                if (user.getInvalidTry() >= 5) {
                    return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(Map.of(
                        "errorCode", "E_TSCL_01_0003"));
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "errorCode", "E_TSCL_01_0001"));
            }
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "errorCode", "An error occured"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        HttpServletRequest request, 
        HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder
        .getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler()
            .logout(request, response, authentication);
        }
        return ResponseEntity.ok("Successfully logged out");
    }


    @GetMapping("/check-auth")
    public ResponseEntity<Void> checkAuth() {
        Authentication auth = SecurityContextHolder
        .getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
