/*
 * UM005Repository
 * Service for updating password
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.repository.UM005Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UM005Service {

    @Autowired
    private UM005Repository um005Repository;

    public boolean isOldPasswordCorrect(
        String accountId, 
        String oldPassword) 
        {
        String hashedPassword = um005Repository
            .findPasswordByAccountId(accountId);
        return hashedPassword != null && BCrypt
            .checkpw(oldPassword, hashedPassword);
    }

    public void updatePassword(
        String accountId, 
        String newPassword) {

        String newHashedPassword = BCrypt
            .hashpw(newPassword, BCrypt.gensalt());
        um005Repository.updatePassword(
            accountId, 
            newHashedPassword);
    }
}
