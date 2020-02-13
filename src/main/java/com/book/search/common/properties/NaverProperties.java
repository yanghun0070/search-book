package com.book.search.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 네이버 책 검색 관련 연동 정보를 property 파일로 부터 로드하는 클래스이다.
 */

@Component
@ConfigurationProperties(prefix = "book.naver")
public class NaverProperties {

    @Getter
    @Setter
    private String uri;

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String clientSecret;
}
