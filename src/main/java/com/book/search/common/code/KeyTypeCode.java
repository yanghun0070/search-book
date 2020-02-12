package com.book.search.common.code;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * kakao를 기본으로 한다.
 * API도 카카오와 동일하게 간다.
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