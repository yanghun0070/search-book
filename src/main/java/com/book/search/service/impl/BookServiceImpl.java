package com.book.search.service.impl;

import com.book.search.common.data.BookResultData;
import com.book.search.common.data.UserData;
import com.book.search.component.BookComponent;
import com.book.search.component.HistoryComponent;
import com.book.search.component.MemberComponent;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.endpoint.model.response.BooksResponse;
import com.book.search.exception.BusinessException;
import com.book.search.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private HistoryComponent historyComponent;
    private MemberComponent memberComponent;
    private BookComponent bookComponent;

    @Autowired
    public BookServiceImpl(MemberComponent memberComponent,
                           BookComponent bookComponent,
                           HistoryComponent historyComponent) {
        this.memberComponent = memberComponent;
        this.bookComponent = bookComponent;
        this.historyComponent = historyComponent;
    }

    @Override
    public BooksResponse search(SearchBookRequest request) throws BusinessException {
        BookResultData bookResultData = bookComponent.loadBooks(request);
        recordKeyword(request.getQuery());

        return buildResponse(bookResultData);
    }

    private BooksResponse buildResponse(BookResultData bookResultData) {
        return new BooksResponse(bookResultData);
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
