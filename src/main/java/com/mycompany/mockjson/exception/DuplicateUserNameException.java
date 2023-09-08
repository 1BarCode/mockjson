package com.mycompany.mockjson.exception;

public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String message) {
        super(message);
    }

    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
