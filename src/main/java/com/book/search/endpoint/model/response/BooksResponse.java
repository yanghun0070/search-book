package com.book.search.endpoint.model.response;

import com.book.search.common.data.BookData;
import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 책 검색 결과 응답데이터 정의
 */
@Builder
@Data
public class BooksResponse {
    @JsonProperty(value = SerializedFieldNames.TOTAL_COUNT)
    private int totalCount;
    @JsonProperty(value = SerializedFieldNames.PAGECOUNT)
    private int pageConunt;
    List<BookData> books;
}
