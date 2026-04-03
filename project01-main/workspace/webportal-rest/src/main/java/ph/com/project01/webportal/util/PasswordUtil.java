/*
 * PasswordUtil.java
 * Password Utility For Generating Random Password
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.util;

import java.util.UUID;

public class PasswordUtil {
    public static String generateUUIDPassword() {
        // Generate a random UUID and convert it to a string
        String uuid = UUID.randomUUID().toString();

        // Remove dashes and limit the length (e.g., 8 characters)
        return uuid.replaceAll("-", "").substring(0, 8); // You can adjust the length
    }
}

