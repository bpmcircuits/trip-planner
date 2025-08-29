package com.kodilla.userapiservice.exception;

public class EmailSendingException extends Exception {
    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}