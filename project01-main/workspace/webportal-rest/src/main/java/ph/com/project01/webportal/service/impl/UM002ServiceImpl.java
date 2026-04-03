/*
 * UM002ServiceImpl.java
 * User Registration Service Implementation
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service.impl;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.DepartmentMaster;
import ph.com.project01.webportal.model.PositionMaster;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.SectionMaster;
import ph.com.project01.webportal.model.UserInfo;
import ph.com.project01.webportal.model.UserMaster;
import ph.com.project01.webportal.model.UserStatus;
import ph.com.project01.webportal.repository.UM002Repository;
import ph.com.project01.webportal.service.UM002Service;
import ph.com.project01.webportal.util.EmailTemplateUtil;
import ph.com.project01.webportal.util.PasswordUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UM002ServiceImpl implements UM002Service {

  private final UM002Repository um002Repository;

  private final PasswordEncoder passwordEncoder;

  private final JavaMailSender mailSender;

  public UM002ServiceImpl(UM002Repository um002Repository,
      PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
    this.um002Repository = um002Repository;
    this.passwordEncoder = passwordEncoder;
    this.mailSender = mailSender;
  }

  @Override
  public String getScreenTitle() {
    StringBuilder screenTitle = new StringBuilder();
    String tier1 = um002Repository.getFunctionName("11").split(" ")[0];
    String tier2 = um002Repository.getFunctionName("111");
    screenTitle.append(tier1).append(" ").append(tier2);
    return screenTitle.toString();
  }

  @Override
  public UserMaster getUserMaster(int userId) {
    return um002Repository.getUserMaster(userId);
  }

  @Override
  public UserInfo getUserInfo(int userId) {
    return um002Repository.getUserInfo(userId);
  }

  @Override
  public void registerUser(UserMaster userMaster,
      UserInfo userInfo,
      String loggedInUsername) {

    String generatedPassword = PasswordUtil.generateUUIDPassword();

    userMaster.setPassword(passwordEncoder.encode(generatedPassword));

    userMaster.setCreateId(loggedInUsername);
    userMaster.setUpdateId(loggedInUsername);
    userInfo.setCreateId(loggedInUsername);
    userInfo.setUpdateId(loggedInUsername);

    int result = um002Repository.insertUser(userMaster, userInfo);

    if (result == -1){
      throw new RuntimeException("Error in inserting user");
    }
    else {
      String emailTemplate = EmailTemplateUtil.getRegistrationEmailTemplate(
      userInfo.getFirstName(), userMaster.getAccountId(), generatedPassword);

      sendEmail(userMaster.getMailaddress(), 
    "Account Created For Your WebPortal Account", emailTemplate);
    }

  }

  @Override
  public List<RoleMaster> getAllRole() {
    return um002Repository.getAllRole();
  }

  @Override
  public List<PositionMaster> getAllPosition() {
    return um002Repository.getAllPosition();
  }

  @Override
  public List<DepartmentMaster> getAllDepartment() {
    return um002Repository.getAllDepartment();
  }

  @Override
  public List<SectionMaster> getAllSection() {
    return um002Repository.getAllSection();
  }

  @Override
  public List<UserStatus> getAllUserStatus() {
    return um002Repository.getAllUserStatus();
  }

  @Override
  public String getNextAssociateId() {
    String nextAssociateId = um002Repository.getNextAssociateId();

    // increment the associate id by 1
    nextAssociateId = String.valueOf(Integer.parseInt(nextAssociateId) + 1);

    return nextAssociateId;

  }

  @Override
  public String findExistingRecords(String associateId, String email, String username) {
    UserMaster userMaster = um002Repository.getUserMasterByAssociateId(associateId);
    if (userMaster != null) {
      return "Associate ID";
    } else if (um002Repository.getUserMasterByUsername(username) != null) {
      return "Username";
    } else if (um002Repository.getUserMasterByEmail(email) != null) {
      return "Email";
    }
    return null;
  }

  private void sendEmail(
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
      e.printStackTrace();
    }
  }
  
}
