package com.book.search.common.data;

import java.util.List;

/**
 * 책 데이터 구조를 정의한다.
 * 카카오 및 네어버 책 데이터가 매핑된다.
 */
public interface BookData {
    List<String> getAuthors();

    String getTitle();

    String getContents();

    String getUrl();

    String getIsbn();

    String getDatetime();

    String getPublisher();

    List<String> getTranslators();

    int getPrice();

    int getSalePrice();

    String getThumbnail();

    String getStatus();
}
