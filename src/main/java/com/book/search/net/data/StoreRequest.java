package com.book.search.net.data;

import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;

/**
 * 책 검색 요청데이터 정의
 */
public interface StoreRequest {

    String getQuery();

    SortCode getSortCode();

    int getPage();

    int getSize();

    KeyTypeCode getKeyTypeCode();

    int getStart();
}
