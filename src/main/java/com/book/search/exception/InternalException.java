package com.book.search.exception;

/**
 * 내부 시스템 및 백앤드 시스템 장애 발생 Exception
 */
public class InternalException extends RuntimeException {

    private static final long serialVersionUID = -3662379426733222100L;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
