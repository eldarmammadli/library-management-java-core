package com.library.exception;

public class LibraryIsEmptyException extends RuntimeException {

    public LibraryIsEmptyException(String message) {
        super(message);
    }
}
