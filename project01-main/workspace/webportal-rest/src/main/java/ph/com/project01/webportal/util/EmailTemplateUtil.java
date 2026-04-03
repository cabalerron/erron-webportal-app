package ph.com.project01.webportal.util;

public class EmailTemplateUtil {

    /**
     * Get password reset email template
     * @param username
     * @param token
     * @param expirationMinutes
     * @return String
     */
    public static String getPasswordResetTemplate(String username, String token, int expirationMinutes) {
        return String.format(
            "<html>" +
            "<head>" +
            "<style>" +
            "body { font-family: 'Roboto', Arial, sans-serif; background-color: #f5f5f5; color: #333; padding: 20px; }" +
            ".container { max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 20px; }" +
            ".header { background-color: #01579b; color: white; padding: 10px; border-radius: 8px 8px 0 0; text-align: center; }" +
            ".button { background-color: #01579b; color: white !important; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; font-weight: bold; }" +
            ".button:hover { background-color: #004c8c; }" +  // Optional hover effect
            ".button-container { text-align: center; margin-top: 20px; }" +
            ".footer { margin-top: 20px; text-align: center; font-size: 12px; color: #777; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'>" +
            "<h2>Password Reset Request</h2>" +
            "</div>" +
            "<p>Good Day,</p>" +
            "<p>We received a request to reset the password for your account.</p>" +
            "<p>If you made this request, please click the button below to reset your password:</p>" +
            "<p>Username: <strong>%s</strong></p>" +
            "<div class='button-container'>" +
            "<a href='http://localhost:3000/PW002?token=%s' class='button'>Password Reset Link</a>" +
            "</div>" +
            "<p>For your security, this link will expire in %d minutes.</p>" +
            "<p>If you did not request a password reset, please ignore this email or contact our support team.</p>" +
            "<div class='footer'>" +
            "<p>Thank you,<br>[Administrators]</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>",
            username, token, expirationMinutes
        );
    }

    /**
     * Get registration email template
     * @param username
     * @param generatedPassword
     * 
     * @return String
     */
    public static String getRegistrationEmailTemplate(String firstName, String username, String generatedPassword){
        return String.format(
            "<html>" +
            "<head>" +
            "<style>" +
            "body { font-family: 'Roboto', Arial, sans-serif; background-color: #f5f5f5; color: #333; padding: 20px; }" +
            ".container { max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 20px; }" +
            ".header { background-color: #01579b; color: white; padding: 10px; border-radius: 8px 8px 0 0; text-align: center; }" +
            ".button { background-color: #01579b; color: white !important; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; font-weight: bold; }" +
            ".button:hover { background-color: #004c8c; }" +  // Optional hover effect
            ".button-container { text-align: center; margin-top: 20px; }" +
            ".footer { margin-top: 20px; text-align: center; font-size: 12px; color: #777; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'>" +
            "<h2>Account Registration</h2>" +
            "</div>" +
            "<p>Hi %s,</p>" +
            "<p>Your account has been successfully created, and you can now log in with the details below:</p>" +
            "<p>Username: <strong>%s</strong></p>" +
            "<p>Password: <strong>%s</strong></p>" +
            "<p>For security reasons, we recommend that you change your password after your first login.</p>" +
            "<p>Thank you !</p>" +
            "<p>Administrator</p>" +
            "</div>" +
            "</body>" +
            "</html>",
            firstName, username, generatedPassword
        );

    }
}
