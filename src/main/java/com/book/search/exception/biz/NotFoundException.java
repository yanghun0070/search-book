package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

/**
 * Not found resource
 */
public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super("not found resource");
    }

    public NotFoundException(String message) {
        super(message);
    }
}