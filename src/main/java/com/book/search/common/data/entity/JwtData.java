package com.book.search.common.data.entity;

import com.book.search.common.util.DateTimeUtils;

public interface JwtData {
    String getJti();

    long getExp();

    default boolean isExpired() {
        return getExp() < DateTimeUtils.getSecondsSinceEpochUTC();
    }

}
