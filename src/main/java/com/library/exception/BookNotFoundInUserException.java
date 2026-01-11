package com.library.exception;

public class BookNotFoundInUserException extends RuntimeException {

    public BookNotFoundInUserException(String message) {
        super(message);
    }
}
