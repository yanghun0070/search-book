package com.book.search.support;

import com.book.search.exception.biz.NotFoundException;

public interface ChainHandler<T, R> {
    R handleRequest(T request) throws NotFoundException;

    R doHandling(T value) throws NotFoundException;

    ChainHandler add(ChainHandler next);
}