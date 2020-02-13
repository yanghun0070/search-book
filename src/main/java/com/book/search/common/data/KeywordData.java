package com.book.search.common.data;

import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 키워드 검색 통계를 데이터를 정의한 entity 클래스 이다.
 */
public interface KeywordData {
    @JsonProperty(value = SerializedFieldNames.KEYWORD)
    String getKeyword();

    @JsonProperty(value = SerializedFieldNames.COUNT)
    String getCount();

}
