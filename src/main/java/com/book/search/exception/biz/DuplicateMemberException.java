package com.book.search.exception.biz;

import com.book.search.exception.BusinessException;

/**
 * 중복데이터 Exception
 */
public class DuplicateMemberException extends BusinessException {

    public DuplicateMemberException(String userId) {
        super(String.format("duplicate entry (%s)", userId));
    }
}
