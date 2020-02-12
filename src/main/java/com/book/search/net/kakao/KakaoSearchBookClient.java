package com.book.search.net.kakao;

import com.book.search.common.properties.KakaoProperties;
import com.book.search.net.SearchClient;
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

@Slf4j
@Component
public class KakaoSearchBookClient implements SearchClient<KakaoBooksResponse> {

    private RestTemplate restTemplate;
    private KakaoProperties properties;
    private HttpHeaders httpHeaders;

    @Autowired
    public KakaoSearchBookClient(RestTemplate restTemplate,
                                 KakaoProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        httpHeaders = initHttpHeaders();
    }

    private HttpHeaders initHttpHeaders() {
        return new HttpHeaders() {{
            set("Authorization", "KakaoAK " + properties.getApiKey());
            setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }};
    }

    @Override
    public KakaoBooksResponse request(StoreRequest request) {
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(resolveUri(request), HttpMethod.GET, entity, KakaoBooksResponse.class).getBody();
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
}
