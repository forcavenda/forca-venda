package br.com.logistica.forcavenda.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.logistica.forcavenda.exception.AppException;

@Configuration
public class TestMailService {

  public static String currentActivateToken;
  public static String currentResetPasswordToken;

  @Bean
  @Primary
  public MailService mailService() throws AppException {
    MailService mailService = Mockito.mock(MailService.class);
    Mockito.doAnswer(invocation -> {
      Object[] args = invocation.getArguments();
      String activationLink = (String) args[0];
      currentActivateToken = activationLink.split("=")[1];
      return null;
    }).when(mailService).sendActivationEmail(Mockito.anyString(), Mockito.anyString());
    Mockito.doAnswer(invocation -> {
      Object[] args = invocation.getArguments();
      String passwordResetLink = (String) args[0];
      currentResetPasswordToken = passwordResetLink.split("=")[1];
      return null;
    }).when(mailService).sendResetPasswordEmail(Mockito.anyString(), Mockito.anyString());
    return mailService;
  }
}
