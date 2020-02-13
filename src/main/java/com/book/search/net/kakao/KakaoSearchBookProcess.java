package com.book.search.net.kakao;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.common.properties.KakaoProperties;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.SearchProcess;
import com.book.search.net.data.StoreRequest;
import com.book.search.net.kakao.model.KakaoBooksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

/**
 * 카카오 책 검색 수행 클래스
 */
@Slf4j
@Component
public class KakaoSearchBookProcess extends SearchProcess {

    private KakaoProperties properties;

    @Autowired
    public KakaoSearchBookProcess(RestTemplate restTemplate,
                                  KakaoProperties properties) {
        super.restTemplate = restTemplate;
        this.properties = properties;
        initialize();
    }

    private void initialize() {
        super.httpHeaders = new HttpHeaders() {{
            set("Authorization", "KakaoAK " + properties.getApiKey());
            setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }};
    }

    private URI resolveUri(StoreRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getUri())
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSortCode().getKakaoCode())
                .queryParam("target", request.getKeyTypeCode().getKakaoCode());

        return builder.build(false).toUri();
    }

    @Override
    public BookResultData doHandling(StoreTranslate value) throws NotFoundException {
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        URI uri = resolveUri(value.translate(BookStoreCode.KAKAO));

        KakaoBooksResponse response = restTemplate
                .exchange(uri, HttpMethod.GET, entity, KakaoBooksResponse.class)
                .getBody();

        if (response.getBooks().size() == 0)
            throw new NotFoundException("no search result to kakao");

        return response;
    }
}
