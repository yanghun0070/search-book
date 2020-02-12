package com.book.search.net.naver.model;

import com.book.search.common.data.BookData;
import com.book.search.common.data.BookResultData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class NaverBooksResponse implements BookResultData {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;

    private List<BookItem> items;


    @Override
    public int getTotalCount() {
        return this.getTotal();
    }

    @Override
    public int getPageCount() {
        if (getTotalCount() == 0)
            return 0;
        return (this.getTotalCount() / display) + ((this.getTotalCount() % display) == 0 ? 0 : 1);
    }

    @Override
    public List<BookData> getBooks() {
        if (getItems() == null)
            return null;
        return items.stream().map(doc -> (BookData) doc).collect(Collectors.toList());
    }
}