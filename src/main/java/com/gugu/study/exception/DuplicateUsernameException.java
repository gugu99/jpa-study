package com.gugu.study.exception;

public class DuplicateUsernameException extends RuntimeException{

    private String message;

    public DuplicateUsernameException(String message) {
        super(message);
        this.message = message;
    }
}
