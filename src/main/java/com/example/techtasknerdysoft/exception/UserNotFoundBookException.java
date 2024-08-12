package com.example.techtasknerdysoft.exception;

public class UserNotFoundBookException extends RuntimeException {
    public UserNotFoundBookException(String message) {
        super(message);
    }
}
