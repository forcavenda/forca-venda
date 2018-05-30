package br.com.logistica.forcavenda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private AppErrorCode errorCode;

  public AppException() {
    super();
  }

  public AppException(final String message) {
    super(message);
  }

  public AppException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AppException(AppErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public AppException(String message, AppErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public AppException(String message, Throwable cause, AppErrorCode errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public AppException(Throwable cause, AppErrorCode errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

  public AppErrorCode getErrorCode() {
    return errorCode;
  }
}
