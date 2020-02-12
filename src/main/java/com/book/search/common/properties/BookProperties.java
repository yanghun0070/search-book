package com.book.search.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "book.auth")
public class BookProperties {

    @Getter
    @Setter
    private String keyPairPath;
}
