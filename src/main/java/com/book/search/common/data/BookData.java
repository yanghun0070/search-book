package com.book.search.common.data;

import java.util.List;

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
