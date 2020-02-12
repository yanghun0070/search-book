package com.book.search.endpoint.controller;

import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.response.TopListResponse;
import com.book.search.service.TopKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopListController {

    @Autowired
    TopKeywordService topKeywordService;

    @GetMapping(Endpoints.SEARCH_TOPS_ENDPOINT)
    public TopListResponse tops() {
        return topKeywordService.retrieve();
    }
}
