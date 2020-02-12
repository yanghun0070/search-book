package com.book.search.common.data;

import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface KeywordData {
    @JsonProperty(value = SerializedFieldNames.KEYWORD)
    String getKeyword();

    @JsonProperty(value = SerializedFieldNames.COUNT)
    String getCount();

}
