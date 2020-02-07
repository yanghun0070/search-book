package com.search.book.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.book.common.message.MessageResolver;
import com.search.book.common.properties.SearchBookConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaAuditing
public class WebConfig {

    @Bean
    public MessageResolver messageResolver() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding(SearchBookConstants.DEFAULT_CHARSET);
        return new MessageResolver(messageSource);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnUnknownProperties(false)
                .build();
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper());
        jackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML,
                MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        jackson2HttpMessageConverter.setPrettyPrint(true);
        return jackson2HttpMessageConverter;
    }
}
