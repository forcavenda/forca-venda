package br.com.logistica.forcavenda.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AppErrorCode {
  GENERAL(2),
  AUTHENTICATION(10),
  JWT_TOKEN_EXPIRED(11),
  PERMISSION_DENIED(20),
  INVALID_ARGUMENTS(30),
  BAD_REQUEST_PARAMS(31),
  ITEM_NOT_FOUND(32);

  private int errorCode;

  AppErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  @JsonValue
  public int getErrorCode() {
    return errorCode;
  }
}
