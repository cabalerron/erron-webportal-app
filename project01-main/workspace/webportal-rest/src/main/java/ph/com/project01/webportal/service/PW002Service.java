/*
 * PW002Service
 * Service for verifying token and updating password
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ph.com.project01.webportal.model.UsersMaster;
import ph.com.project01.webportal.repository.PW002Repository;
import ph.com.project01.webportal.util.LogUtil;

@Service
public class PW002Service {

    @Autowired
    private PW002Repository pw002Repository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private LogUtil logger = new LogUtil(); 

    public UsersMaster findByResetToken(String token) {
        try {
            return pw002Repository.findByResetToken(token);
        } catch (Exception e) {
            logger.error("Error finding user by reset token: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean isTokenValid(UsersMaster user) {
        try {
            return user != null && user.getTokenExp() != null && user.getTokenExp().isAfter(LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Error validating token: " + e.getMessage(), e);
            return false;
        }
    }

    public void updatePasswordByToken(String token, String newPassword) {
        try {
            String hashedPassword = passwordEncoder.encode(newPassword);
            pw002Repository.updatePasswordByToken(token, hashedPassword);
        } catch (Exception e) {
            logger.error("Error updating password: " + e.getMessage(), e);
        }
    }
}
