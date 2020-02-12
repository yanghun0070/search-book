package com.book.search.error;

import com.book.search.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public interface ErrorSources {

    HttpStatus getHttpStatus();

    ErrorCode getErrorCode();

    Object[] getArgs();
}
