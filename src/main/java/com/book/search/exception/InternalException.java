package com.book.search.exception;

public class InternalException extends RuntimeException {

    private static final long serialVersionUID = -3662379426733222100L;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
