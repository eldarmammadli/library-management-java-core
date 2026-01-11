package com.library.exception;

public class NoUsersFoundException extends RuntimeException {

    public NoUsersFoundException(String message) {
        super(message);
    }
}
