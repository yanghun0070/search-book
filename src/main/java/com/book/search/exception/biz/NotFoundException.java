package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super("not found resource");
    }
}