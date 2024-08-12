package com.example.techtasknerdysoft.exception;

public class UserHasMaximumBooksException extends RuntimeException {
    public UserHasMaximumBooksException(String message) {
        super(message);
    }
}
