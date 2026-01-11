package com.library.exception;

public class BookBorrowedException extends RuntimeException {

    public BookBorrowedException(String message) {
        super(message);
    }
}
