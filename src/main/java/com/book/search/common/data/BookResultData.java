package com.book.search.common.data;

import java.util.List;

/**
 * 책 검색 결과 데이터를 정의한다.
 */
public interface BookResultData {
    int getTotalCount();

    int getPageCount();

    List<BookData> getBooks();
}
