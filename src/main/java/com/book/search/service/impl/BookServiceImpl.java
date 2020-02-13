package com.book.search.service.impl;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.UserData;
import com.book.search.component.HistoryComponent;
import com.book.search.component.StoreBookComponent;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.endpoint.model.response.BooksResponse;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.exception.http.ErrorException;
import com.book.search.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private HistoryComponent historyComponent;
    private StoreBookComponent storeBookComponent;

    @Autowired
    public BookServiceImpl(StoreBookComponent storeBookComponent,
                           HistoryComponent historyComponent) {
        this.storeBookComponent = storeBookComponent;
        this.historyComponent = historyComponent;
    }

    @Override
    public BooksResponse search(SearchBookRequest request) {
        BookResultData bookResultData;
        try {
            bookResultData = storeBookComponent.loadBooks(request);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw new ErrorException(HttpStatus.NO_CONTENT, ErrorCode.NOT_FOUND_RESOURCE, e.getMessage());
        }

        recordKeyword(request.getQuery());

        return buildResponse(bookResultData);
    }

    private BooksResponse buildResponse(BookResultData bookResultData) {
        return BooksResponse.builder()
                .books(bookResultData.getBooks())
                .totalCount(bookResultData.getTotalCount())
                .pageConunt(bookResultData.getPageCount())
                .build();
    }

    /**
     * 검색결과 유무는 체크하지 않음
     */
    private void recordKeyword(String keyword) {
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            historyComponent.saveHistory(keyword, userData.getMemberId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Keyword record failed. keyword:{}, member id:{}", keyword, userData.getMemberId());
        }

    }
}
