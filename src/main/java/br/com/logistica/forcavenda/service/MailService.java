package br.com.logistica.forcavenda.service;

import br.com.logistica.forcavenda.exception.AppException;

public interface MailService {

  public void updateMailConfiguration();

  public void sendEmail(String email, String subject, String message) throws AppException;

  public void sendTestMail(String email) throws AppException;

  public void sendActivationEmail(String activationLink, String email) throws AppException;

  public void sendAccountActivatedEmail(String loginLink, String email) throws AppException;

  public void sendResetPasswordEmail(String passwordResetLink, String email) throws AppException;

  public void sendPasswordWasResetEmail(String loginLink, String email) throws AppException;
}