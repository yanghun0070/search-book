package com.book.search.net.naver;

import com.book.search.exception.InternalException;
import com.book.search.net.naver.model.NaverBooksResponse;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Service
public class NaverSearchBookClientFallback implements FallbackFactory<NaverSearchBookClient> {

    @Override
    public NaverSearchBookClient create(Throwable cause) {
        if (cause == null) {
            log.error("naver null fallback");
            throw new RuntimeException("naver  service not response");
        } else if (cause instanceof FeignException) {
            FeignException exc = (FeignException) cause;
            log.error("naver FeignExcpeiton fallback is [{}]", exc.getMessage());
        } else if (cause instanceof HttpStatusCodeException) {
            HttpStatusCodeException exc = (HttpStatusCodeException) cause;
            log.error("status[{}]\n{}", exc.getStatusCode(), cause.getMessage());
            throw new RuntimeException("naver service not response");
        } else {
            log.error("{}", cause.getMessage());
            throw new RuntimeException("naver service not response");
        }

        return new NaverSearchBookClient() {
            @Override
            public NaverBooksResponse request(String clientId, String secret, String query, int page, int size, String sort, String target) {
                throw new InternalException("Member service error");
            }

        };
    }
}