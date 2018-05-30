package br.com.logistica.forcavenda.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.NestedRuntimeException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import br.com.logistica.forcavenda.exception.AppErrorCode;
import br.com.logistica.forcavenda.exception.AppException;
import br.com.logistica.forcavenda.service.MailService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@PropertySource("classpath:mail.properties")
public class MailServiceImpl implements MailService {

  public static final String TARGET_EMAIL = "targetEmail";

  @Value("${mail.host}")
  private String host;

  @Value("${mail.port}")
  private int port;

  @Value("${mail.protocol}")
  private String protocol;

  @Value("${mail.encoding}")
  private String encoding;

  @Value("${mail.smtp.starttls.enable}")
  private String starttls;

  @Value("${mail.user}")
  private String userMail;

  @Value("${mail.pass}")
  private String passMail;

  @Autowired
  private MessageSource messages;

  @Autowired
  @Qualifier("velocityEngine")
  private VelocityEngine velocityEngine;

  private JavaMailSender mailSender;
  private String mailFrom;

  @PostConstruct
  private void init() {
    updateMailConfiguration();
  }

  @Override
  public void updateMailConfiguration() {
    mailSender = createMailSender();
    mailFrom = userMail;
  }

  @Bean(name = "javaMailSender")
  public JavaMailSender createMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setProtocol(protocol);
    mailSender.setUsername(userMail);
    mailSender.setPassword(passMail);
    mailSender.setDefaultEncoding(encoding);
    mailSender.setJavaMailProperties(createJavaMailProperties());
    return mailSender;
  }

  private Properties createJavaMailProperties() {
    Properties properties = new Properties();
    properties.setProperty("mail.smtp.starttls.enable", starttls);
    properties.setProperty("mail.smtp.host", host);
    // properties.setProperty("mail.smtp.port", port);
    // properties.setProperty("mail.smtp.timeout", 1000);
    properties.setProperty("mail.smtp.auth", String.valueOf(StringUtils.isNotEmpty(userMail)));
    return properties;
  }

  @Override
  public void sendEmail(String email, String subject, String message) throws AppException {
    sendMail(mailSender, mailFrom, email, subject, message);
  }

  @Override
  public void sendTestMail(String email) throws AppException {
    JavaMailSender testMailSender = createMailSender();
    String subject = messages.getMessage("test.message.subject", null, Locale.US);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put(TARGET_EMAIL, email);
    String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "test.vm",
      encoding,
      model);
    sendMail(testMailSender, mailFrom, email, subject, message);
  }

  @Override
  public void sendActivationEmail(String activationLink, String email) throws AppException {
    String subject = messages.getMessage("activation.subject", null, Locale.US);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("activationLink", activationLink);
    model.put(TARGET_EMAIL, email);
    String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "activation.vm",
      encoding,
      model);
    sendMail(mailSender, mailFrom, email, subject, message);
  }

  @Override
  public void sendAccountActivatedEmail(String loginLink, String email) throws AppException {
    String subject = messages.getMessage("account.activated.subject", null, Locale.US);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("loginLink", loginLink);
    model.put(TARGET_EMAIL, email);
    String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
      "account.activated.vm",
      encoding, model);
    sendMail(mailSender, mailFrom, email, subject, message);
  }

  @Override
  public void sendResetPasswordEmail(String passwordResetLink, String email) throws AppException {
    String subject = messages.getMessage("reset.password.subject", null, Locale.US);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("passwordResetLink", passwordResetLink);
    model.put(TARGET_EMAIL, email);
    String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
      "reset.password.vm",
      encoding, model);
    sendMail(mailSender, mailFrom, email, subject, message);
  }

  @Override
  public void sendPasswordWasResetEmail(String loginLink, String email) throws AppException {
    String subject = messages.getMessage("password.was.reset.subject", null, Locale.US);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("loginLink", loginLink);
    model.put(TARGET_EMAIL, email);
    String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
      "password.was.reset.vm",
      encoding, model);
    sendMail(mailSender, mailFrom, email, subject, message);
  }

  private void sendMail(JavaMailSender mailSender,
      String mailFrom, String email,
      String subject, String message) throws AppException {
    try {
      MimeMessage mimeMsg = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, encoding);
      helper.setFrom(mailFrom);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(message, true);
      helper.setSentDate(new Date());
      mailSender.send(helper.getMimeMessage());
    } catch (Exception e) {
      throw handleException(e);
    }
  }

  protected AppException handleException(Exception exception) {
    String message;
    if (exception instanceof NestedRuntimeException) {
      message = ((NestedRuntimeException) exception).getMostSpecificCause().getMessage();
    } else {
      message = exception.getMessage();
    }
    return new AppException(String.format("Unable to send mail: %s", message),
      AppErrorCode.GENERAL);
  }

}
