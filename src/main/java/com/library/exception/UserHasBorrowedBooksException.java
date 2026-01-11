package com.library.exception;

public class UserHasBorrowedBooksException extends RuntimeException {

    public UserHasBorrowedBooksException(String message) {
        super(message);
    }
}
