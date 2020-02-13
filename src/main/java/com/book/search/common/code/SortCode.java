package com.book.search.common.code;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 책 검색 sort code를 정의한다.
 * 카카오와 naver의 코드를 매핑한다.
 *
 */
public enum SortCode {
    ACCURACY("accuracy", "accuracy", "sim"), //default
    LASTEST("latest", "latest", "date");

    private String code;
    private String kakaoCode;
    private String naverCode;

    SortCode(String code, String kakaoCode, String naverCode) {
        this.code = code;
        this.kakaoCode = kakaoCode;
        this.naverCode = naverCode;
    }

    private final static Map<String, SortCode> sortCodeMap = Arrays.stream(SortCode.values())
            .collect(Collectors.toMap(SortCode::getCode, o -> o));

    public String getCode() {
        return code;
    }

    public String getKakaoCode() {
        return kakaoCode;
    }

    public String getNaverCode() {
        return naverCode;
    }

    public static SortCode resolveKeyType(String key) {
        return sortCodeMap.get(key) == null ? ACCURACY : sortCodeMap.get(key);
    }

}