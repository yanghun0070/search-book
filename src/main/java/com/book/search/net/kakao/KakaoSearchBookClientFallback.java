package com.book.search.net.kakao;

import com.book.search.exception.InternalException;
import com.book.search.net.kakao.model.KakaoBooksResponse;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Service
public class KakaoSearchBookClientFallback implements FallbackFactory<KakaoSearchBookClient> {

    @Override
    public KakaoSearchBookClient create(Throwable cause) {
        if (cause == null) {
            log.error("kakao null fallback");
            throw new RuntimeException("kakao service not response");
        } else if (cause instanceof FeignException) {
            FeignException exc = (FeignException) cause;
            log.error("kakaoFeignExcpeiton fallback is [{}]", exc.getMessage());
        } else if (cause instanceof HttpStatusCodeException) {
            HttpStatusCodeException exc = (HttpStatusCodeException) cause;
            log.error("status[{}]\n{}", exc.getStatusCode(), cause.getMessage());
            throw new RuntimeException("kakao service not response");
        } else {
            log.error("{}", cause.getMessage());
            throw new RuntimeException("kakao service not response");
        }

        return new KakaoSearchBookClient() {
            @Override
            public KakaoBooksResponse request(String authorization, String query, int page, int size, String sort, String target) {
                throw new InternalException("Kakao service error");
            }
        };
    }
}
