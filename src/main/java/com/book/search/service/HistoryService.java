package com.book.search.service;

import com.book.search.endpoint.model.response.HistoryResponse;

public interface HistoryService {
    HistoryResponse select(int size, int page);
}
