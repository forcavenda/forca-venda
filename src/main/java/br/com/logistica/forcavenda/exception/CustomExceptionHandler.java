package br.com.logistica.forcavenda.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mongodb.MongoException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

  @ExceptionHandler(MongoException.class)
  public ResponseEntity handleMongoException(final MongoException exception) {
    log.warn("Processing mongo exception: {}", exception.getMessage());

    return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity handleServiceException(final AppException exception) {
    log.warn("Processing service exception: {}", exception.getMessage());

    return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity handleUserNotFoundException(final ResourceNotFoundException exception) {
    log.warn("Processing user not found exception: {}", exception.getMessage());

    return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleAbstractException(final Exception exception) {
    log.warn("Processing abstract exception: {}", exception.getMessage());

    return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
  }
}
