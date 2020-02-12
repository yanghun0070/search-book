package com.book.search.endpoint.model.response;

import com.book.search.common.data.BookData;
import com.book.search.common.data.BookResultData;
import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BooksResponse {
    @JsonProperty(value = SerializedFieldNames.TOTAL_COUNT)
    private int totalCount;
    @JsonProperty(value = SerializedFieldNames.PAGECOUNT)
    private int pageConunt;
    List<BookData> books;

    public BooksResponse(BookResultData bookResultData) {
        this.totalCount = bookResultData.getTotalCount();
        this.pageConunt = bookResultData.getPageCount();
        this.books = bookResultData.getBooks();
    }
}
