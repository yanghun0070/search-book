package com.book.search.exception.http;

import com.book.search.common.code.ErrorCode;
import com.book.search.error.ErrorSources;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * error 발생시 처리 Exception
 */
@ToString
public class ErrorException extends RuntimeException implements ErrorSources {

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private Object[] args;


    public ErrorException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public ErrorException(HttpStatus httpStatus, ErrorCode errorCode, Object... args) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }
}
