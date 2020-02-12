package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

public class InvaildRequestParameterException extends BusinessException {
    public InvaildRequestParameterException() {
        super("invaild parameter");
    }
}
