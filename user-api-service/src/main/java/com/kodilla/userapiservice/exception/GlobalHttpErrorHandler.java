package com.kodilla.userapiservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given id not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        return new ResponseEntity<>("User with this email already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<Object> handleInvalidVerificationCodeException(InvalidVerificationCodeException exception) {
        return new ResponseEntity<>("Invalid verification code", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Object> handleEmailSendingException(EmailSendingException exception) {
        return new ResponseEntity<>("Failed to send email: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<Object> handleLoginAlreadyExistsException(LoginAlreadyExistsException exception) {
        return new ResponseEntity<>("User with this login already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        return new ResponseEntity<>("An error occurred: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}