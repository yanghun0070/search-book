package com.book.search.net.kakao;

import com.book.search.net.BookFeignClientConfiguration;
import com.book.search.net.kakao.model.KakaoBooksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 카카오 책 검색 수행 클래스
 */
@FeignClient(value = "kakao-store",
        url = "${book.kakao.uri}",
        configuration = {BookFeignClientConfiguration.class},
        fallbackFactory = KakaoSearchBookClientFallback.class)
public interface KakaoSearchBookClient {
    @GetMapping(value = "/v3/search/book")
    KakaoBooksResponse request(@RequestHeader("Authorization") String authorization,
                               @RequestParam(value = "query") String query,
                               @RequestParam(value = "page") int page,
                               @RequestParam(value = "size") int size,
                               @RequestParam(value = "sort") String sort,
                               @RequestParam(value = "target") String target);
}