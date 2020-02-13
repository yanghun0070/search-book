package com.book.search.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * book 인증관련 정보를 property 파일로 부터 로드하는 클래스이다.
 */
@Component
@ConfigurationProperties(prefix = "book.auth")
public class BookProperties {

    @Getter
    @Setter
    private String keyPairPath;
}
