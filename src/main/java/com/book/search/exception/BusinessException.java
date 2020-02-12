package com.book.search.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 5487572737310597193L;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
