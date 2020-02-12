package com.book.search.service;

import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.endpoint.model.response.BooksResponse;
import com.book.search.exception.BusinessException;

public interface BookService {
    BooksResponse search(SearchBookRequest request);
}
