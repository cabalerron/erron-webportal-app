/*
 * PW001Service
 * Service for password reset email 
 *
 * v 00.001 - 10/22/2024
 *
 * PIC: emonteverde
 */

 package ph.com.project01.webportal.service;

 import java.time.LocalDateTime;
 import java.util.UUID;
 
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.mail.javamail.MimeMessageHelper;
 import org.springframework.stereotype.Service;
 
 import ph.com.project01.webportal.model.UsersMaster;
 import ph.com.project01.webportal.repository.PW001Repository;
 import ph.com.project01.webportal.util.LogUtil;
 
 import jakarta.mail.MessagingException;
 import jakarta.mail.internet.MimeMessage;
 
 import ph.com.project01.webportal.util.EmailTemplateUtil;
 
 @Service
 public class PW001Service {
 
     private final LogUtil logger;
     private final JavaMailSender mailSender;
 
     @Value("${password.reset.token.expiration.minutes}")
     private int tokenExpirationMinutes;
 
     @Autowired
     private PW001Repository pw001Repository;
 
     public PW001Service(JavaMailSender mailSender) {
         this.mailSender = mailSender;
         this.logger = new LogUtil();
     }
 
     public void sendEmail(
         String to, 
         String subject, 
         String htmlContent) {
     try {
             MimeMessage message = mailSender.createMimeMessage();
             
             MimeMessageHelper helper = new MimeMessageHelper(
                 message, 
                 true); 
 
             helper.setTo(to);
             helper.setSubject(subject);
             helper.setText(htmlContent, true); 
             mailSender.send(message);
 
         } catch (MessagingException e) {
             logger.error("Failed to send email to " + to, e);
         }
     }
 
     public boolean isEmailRegistered(
         String email) {
         try {
             return pw001Repository
                 .findByMailAddress(email) != null;
         } catch (Exception e) {
             logger.error("Failed to check if email is registered: " + 
             email, e);
             return false;
         }
     }
 
     public void createPasswordResetToken(
         String email) {
         try {
             UsersMaster user = pw001Repository
                 .findByMailAddress(email);
 
             if (user != null) {
                 String token = UUID.randomUUID().toString();
                 user.setResetToken(token);
 
                 LocalDateTime tokenExp = LocalDateTime.now()
                     .plusMinutes(tokenExpirationMinutes);
                 user.setTokenExp(tokenExp);
 
                 pw001Repository.save(user);
 
                 // Define the email template
                 String emailTemplate = EmailTemplateUtil
                     .getPasswordResetTemplate(
                     user.getAccountId(), 
                     token, 
                     tokenExpirationMinutes
                 );
 
                 sendEmail(email, "Password Reset Request", 
                         emailTemplate);
             }
         } catch (Exception e) {
             logger.error("Failed to create password reset token for email " + 
             email, e);
         }
     }
 }