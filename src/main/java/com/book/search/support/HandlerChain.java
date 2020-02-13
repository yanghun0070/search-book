package com.book.search.support;

import com.book.search.exception.biz.NotFoundException;

/**
 * chainHandler call 수행
 * @param <V>
 * @param <T>
 */
public interface HandlerChain<V, T> {
    T makeRequest(V request) throws NotFoundException;
}
