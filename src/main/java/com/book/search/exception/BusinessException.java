package com.book.search.exception;

/**
 * 비즈니스 로직 에러 발생 Exception
 */
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
