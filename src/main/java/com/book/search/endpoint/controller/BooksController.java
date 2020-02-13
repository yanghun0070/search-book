package com.book.search.endpoint.controller;

import com.book.search.common.code.KeyTypeCode;
import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.PathVariables;
import com.book.search.endpoint.SerializedFieldNames;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.endpoint.model.response.BooksResponse;
import com.book.search.exception.BusinessException;
import com.book.search.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 책 검색 controller
 */
@Slf4j
@Validated
@RestController
public class BooksController {

    @Autowired
    BookService bookService;

    @GetMapping(Endpoints.SEARCH_BOOK_ENDPOINT)
    public BooksResponse search(@PathVariable(PathVariables.KEY_TYPE) String keyType,
                                @RequestParam(name = SerializedFieldNames.QUERY) String query,
                                @RequestParam(name = SerializedFieldNames.SORT, required = false) String sort,
                                @RequestParam(name = SerializedFieldNames.SIZE, defaultValue = "10") int size,
                                @RequestParam(name = SerializedFieldNames.PAGE, defaultValue = "1") int page) throws BusinessException {
        log.debug("[BooksController] size:{}, page:{}", size, page);
        KeyTypeCode keyTypeCode = KeyTypeCode.resolveKeyType(keyType);
        return bookService.search(new SearchBookRequest(query, keyTypeCode, sort, size, page));
    }
}