package com.book.search.exception;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.message.ErrorResponse;
import com.book.search.common.message.ErrorResponseResolver;
import com.book.search.exception.http.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * Exception handler
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.book.search"})
public class SearchBookExceptionHandler {

    @Autowired
    private ErrorResponseResolver errorResponseResolver;

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> notSupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getContentType());
    }

    @ExceptionHandler(java.util.NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> noSuchElementException(java.util.NoSuchElementException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> nullPointerException(NullPointerException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> notMethodSupportException(HttpRequestMethodNotSupportedException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXIST, "resource");
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST_INFO, ex.getParameterName());
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponse> internalExceptionHandler(InternalException e) {
        log.error("[InternalException] message=" + e.getMessage(), e);
        e.printStackTrace();
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e) {
        log.error("[InternalException] message=" + e.getMessage(), e);
        e.printStackTrace();
        return getResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> errorExceptionHandler(ErrorException e) {
        log.error("[InternalException] message=" + e.getMessage(), e);
        e.printStackTrace();
        return getResponseEntity(e.getHttpStatus(), e.getErrorCode(), e.getArgs());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        log.error("[Exception] message=" + e.getMessage(), e);
        e.printStackTrace();
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(HttpStatus httpStatus, ErrorCode responseCode, Object... args) {
        return new ResponseEntity(errorResponseResolver.getErrorResponse(responseCode, args), httpStatus);
    }

}