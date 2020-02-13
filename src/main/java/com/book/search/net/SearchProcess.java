package com.book.search.net;


import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.support.ChainHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * 체인 검색 기능 처리 클래스
 */
@Slf4j
public abstract class SearchProcess implements ChainHandler<StoreTranslate, BookResultData> {

    private ChainHandler<StoreTranslate, BookResultData> next;

    @Override
    public BookResultData handleRequest(StoreTranslate request) throws NotFoundException {
        try {
            return doHandling(request);
        } catch (Exception e) {
            log.error("search book store error({})", e.getMessage());
            if (next == null)
                throw e;

            return next.handleRequest(request);
        }
    }

    @Override
    public ChainHandler add(ChainHandler next) {
        this.next = next;
        return next;
    }
}
