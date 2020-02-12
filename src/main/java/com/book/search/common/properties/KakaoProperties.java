package com.book.search.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "book.kakao")
public class KakaoProperties {

    @Getter
    @Setter
    private String uri;

    @Getter
    @Setter
    private String apiKey;
}
