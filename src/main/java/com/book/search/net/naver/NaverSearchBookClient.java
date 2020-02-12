package com.book.search.net.naver;

import com.book.search.common.properties.NaverProperties;
import com.book.search.net.SearchClient;
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
public class NaverSearchBookClient implements SearchClient<NaverBooksResponse> {

    private RestTemplate restTemplate;
    private NaverProperties properties;
    private HttpHeaders httpHeaders;

    @Autowired
    public NaverSearchBookClient(RestTemplate restTemplate,
                                 NaverProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        httpHeaders = initHttpHeaders();
    }

    private HttpHeaders initHttpHeaders() {
        return new HttpHeaders() {{
            set("X-Naver-Client-Id", properties.getClientId());
            set("X-Naver-Client-Secret", properties.getClientSecret());
            setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }};
    }

    @Override
    public NaverBooksResponse request(StoreRequest request) {
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(resolveUri(request), HttpMethod.GET, entity, NaverBooksResponse.class).getBody();
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
}
