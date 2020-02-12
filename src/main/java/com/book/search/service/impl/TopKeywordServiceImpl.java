package com.book.search.service.impl;

import com.book.search.common.code.ErrorCode;
import com.book.search.component.HistoryComponent;
import com.book.search.endpoint.model.response.TopListResponse;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.exception.http.ErrorException;
import com.book.search.service.TopKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TopKeywordServiceImpl implements TopKeywordService {

    private HistoryComponent historyComponent;

    @Autowired
    public TopKeywordServiceImpl(HistoryComponent historyComponent) {
        this.historyComponent = historyComponent;
    }

    @Override
    public TopListResponse retrieve() {
        try {
            return TopListResponse.builder()
                    .keywords(historyComponent.loadKeywordData())
                    .build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.NO_CONTENT, ErrorCode.NOT_FOUND_RESOURCE, "top keyword");
        }
    }
}
