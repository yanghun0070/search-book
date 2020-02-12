package com.book.search.net.naver;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.common.properties.NaverProperties;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.SearchProcess;
import com.book.search.net.data.StoreRequest;
import com.book.search.net.naver.model.NaverBooksResponse;
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

@Slf4j
@Component
public class NaverSearchBookProcess extends SearchProcess {

    private NaverProperties properties;

    @Autowired
    public NaverSearchBookProcess(RestTemplate restTemplate,
                                  NaverProperties properties) {
        super.restTemplate = restTemplate;
        this.properties = properties;
        initialize();
    }

    private void initialize() {
        super.httpHeaders = new HttpHeaders() {{
            set("X-Naver-Client-Id", properties.getClientId());
            set("X-Naver-Client-Secret", properties.getClientSecret());
            setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }};
    }

    private URI resolveUri(StoreRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getUri())
                .queryParam("query", request.getQuery())
                .queryParam("start", request.getStart())
                .queryParam("display", request.getSize())
                .queryParam("sort", request.getSortCode().getNaverCode())
                .queryParam("target", request.getKeyTypeCode().getNaverCode());

        return builder.build(false).toUri();
    }

    @Override
    public BookResultData doHandling(StoreTranslate value) throws NotFoundException {
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        URI uri = resolveUri(value.translate(BookStoreCode.NAVER));

        NaverBooksResponse response = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverBooksResponse.class).getBody();
        log.debug("$######################");
        log.debug("$######################");
        log.debug("naver response:" + response);
        log.debug("naver getBook:" + response.getBooks());
        log.debug("naver size:" + response.getBooks().size());
        log.debug("$######################");
        log.debug("$######################");
        if (response.getBooks() == null || response.getBooks().size() == 0)
            throw new NotFoundException("no search result to naver");

        return response;
    }

}
