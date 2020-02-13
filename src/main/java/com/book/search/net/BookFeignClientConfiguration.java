package com.book.search.net;

import com.fasterxml.jackson.annotation.JsonInclude;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Configuration
public class BookFeignClientConfiguration {

    private ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(getObjectFactory());
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(getObjectFactory()));
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 200, 2);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (String s, Response response) -> {
            HttpHeaders responseHeaders = new HttpHeaders();
            for (Map.Entry<String, Collection<String>> entry : response.headers().entrySet()) {
                responseHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            HttpStatus statusCode = HttpStatus.valueOf(response.status());
            String statusText = response.reason() == null ? "" : response.reason();

            byte[] responseBody;
            try {
                responseBody = IOUtils.toByteArray(response.body().asInputStream());
//                log.debug("member responseBody : " + new String(responseBody));
            } catch (IOException e) {
                throw new RuntimeException("member failed to process response body.", e);
            } catch (NullPointerException e) {
                responseBody = null;
            }

            if (response.status() == 503)
                return new RetryableException(response.status(), "요청이 성공하지 못했습니다.", null, null, null);

            if (response.status() >= 400 && response.status() <= 499) {
                return new HttpClientErrorException(statusCode, statusText, responseHeaders, responseBody, Charset.forName("UTF-8"));
            }

            if (response.status() >= 500 && response.status() <= 599) {
                return new HttpServerErrorException(statusCode, statusText, responseHeaders, responseBody, Charset.forName("UTF-8"));
            }
            return defaultErrorDecoder.decode(s, response);

        };
    }

    public ObjectFactory getObjectFactory() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.TEXT_HTML));
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(mappingJackson2HttpMessageConverter);
        return objectFactory;
    }

}


