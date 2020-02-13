package com.book.search.error;

import com.book.search.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 에러 처리 데이터 구조 정의 클래스
 */
public interface ErrorSources {

    HttpStatus getHttpStatus();

    ErrorCode getErrorCode();

    Object[] getArgs();
}
