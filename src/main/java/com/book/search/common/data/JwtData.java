package com.book.search.common.data;

import com.book.search.common.util.DateTimeUtils;

/**
 * jwt 기본 구조를 정의한 인터페이스이다.
 */
public interface JwtData {
    String getJti();

    long getExp();

    default boolean isExpired() {
        return getExp() < DateTimeUtils.getSecondsSinceEpochUTC();
    }

}
