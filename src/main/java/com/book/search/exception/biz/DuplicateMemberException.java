package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

public class DuplicateMemberException extends BusinessException {

    public DuplicateMemberException(String userId) {
        super(String.format("duplicate entry (%s)", userId));
    }
}
