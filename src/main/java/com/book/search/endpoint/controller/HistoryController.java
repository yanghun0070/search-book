package com.book.search.endpoint.controller;

import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.SerializedFieldNames;
import com.book.search.endpoint.model.response.HistoryResponse;
import com.book.search.exception.BusinessException;
import com.book.search.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 검색 내역 controller
 */
@Validated
@RestController
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @GetMapping(Endpoints.SEARCH_HISTORY_ENDPOINT)
    public HistoryResponse history(@RequestParam(name = SerializedFieldNames.SIZE, required = false, defaultValue = "10") int size,
                                   @RequestParam(name = SerializedFieldNames.PAGE, required = false, defaultValue = "1") int page) {
        return historyService.select(size, page);
    }
}