package com.book.search.net.naver;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.common.properties.NaverProperties;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.SearchProcess;
import com.book.search.net.data.StoreRequest;
import com.book.search.net.naver.model.NaverBooksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 카카오 책 검색 수행 클래스
 */
@Slf4j
@Component
public class NaverSearchBookService extends SearchProcess {

    private NaverProperties properties;
    private NaverSearchBookClient naverSearchBookClient;

    @Autowired
    public NaverSearchBookService(@Lazy NaverSearchBookClient naverSearchBookClient,
                                  NaverProperties properties) {
        this.naverSearchBookClient = naverSearchBookClient;
        this.properties = properties;
    }

    @Override
    public BookResultData doHandling(StoreTranslate value) throws NotFoundException {
        StoreRequest request = value.translate(BookStoreCode.NAVER);
        NaverBooksResponse response = naverSearchBookClient.request(
                properties.getClientId(),
                properties.getClientSecret(),
                request.getQuery(),
                request.getStart(),
                request.getSize(),
                request.getSortCode().getNaverCode(),
                request.getKeyTypeCode().getNaverCode());

        if (response.getBooks().size() == 0)
            throw new NotFoundException("no search result to Naver");

        return response;
    }
}
