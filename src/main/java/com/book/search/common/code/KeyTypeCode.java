package com.book.search.common.code;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 검색 카테고리를 정의한 클래스이다.
 * 제목, isbn, pushisher, personㅡㅇ로 구성되며, keyword가 default로 구성된다.
 * kakao를 기본으로 네이버 매핑한다.
 *
 */
public enum KeyTypeCode {
    TITLE("title", "title", "d_titl"),
    ISBN("isbn", "isbn", "d_isbn"),
    PUBLISHER("publisher", "publisher", "d_publ"),
    PERSON("person", "person", "d_auth"),
    DEFAULT("keyword", "", "");

    private String code;
    private String kakaoCode;
    private String naverCode;

    KeyTypeCode(String code, String kakaoCode, String naverCode) {
        this.code = code;
        this.kakaoCode = kakaoCode;
        this.naverCode = naverCode;
    }

    private final static Map<String, KeyTypeCode> keywordMap = Arrays.stream(KeyTypeCode.values())
            .collect(Collectors.toMap(KeyTypeCode::getCode, o -> o));

    public String getCode() {
        return code;
    }

    public String getKakaoCode() {
        return kakaoCode;
    }

    public String getNaverCode() {
        return naverCode;
    }

    public static KeyTypeCode resolveKeyType(String key) {
        return keywordMap.get(key) == null ? DEFAULT : keywordMap.get(key);
    }
}