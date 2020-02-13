package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

/**
 * 잘못된 요청 파라미터
 */
public class InvaildRequestParameterException extends BusinessException {
    public InvaildRequestParameterException() {
        super("invaild parameter");
    }
}
