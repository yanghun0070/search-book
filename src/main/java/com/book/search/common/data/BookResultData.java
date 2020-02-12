package com.book.search.common.data;

import java.util.List;

public interface BookResultData {
    int getTotalCount();

    int getPageCount();

    List<BookData> getBooks();
}
