package com.book.search.component;

import com.book.search.common.data.BookResultData;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.exception.InternalException;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.SearchProcessManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class StoreBookComponent {

    private SearchProcessManager searchProcessManager;

    @Autowired
    public StoreBookComponent(SearchProcessManager searchProcessManager) {
        this.searchProcessManager = searchProcessManager;
    }

    public BookResultData loadBooks(SearchBookRequest request) throws NotFoundException {
        try {
            return searchProcessManager.makeRequest(request);
        } catch (RestClientException ex) {
            throw new InternalException("resource server error");
        }
    }
}
