package com.book.search.support;

import com.book.search.exception.biz.NotFoundException;

public interface HandlerChain<V, T> {
    T makeRequest(V request) throws NotFoundException;
}
