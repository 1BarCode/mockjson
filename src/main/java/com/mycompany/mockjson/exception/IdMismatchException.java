package com.mycompany.mockjson.exception;

public class IdMismatchException extends Exception {
    public IdMismatchException(String message) {
        super(message);
    }

    public IdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
