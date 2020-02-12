package com.book.search.net.kakao.model;

import com.book.search.common.data.BookData;
import com.book.search.common.data.BookResultData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class KakaoBooksResponse implements BookResultData {
    private MetaData meta;
    private List<BookDocument> documents;

    @Override
    public int getTotalCount() {
        return this.meta.getTotalCount();
    }

    @Override
    public int getPageCount() {
        return this.meta.getPageableCount();
    }

    @Override
    public List<BookData> getBooks() {
        return documents.stream().map(doc -> (BookData) doc).collect(Collectors.toList());
    }
}

@Data
class MetaData {
    @JsonProperty(value = "is_end")
    private boolean isEnd;
    @JsonProperty(value = "pageable_count")
    private int pageableCount;
    @JsonProperty(value = "total_count")
    private int totalCount;
}