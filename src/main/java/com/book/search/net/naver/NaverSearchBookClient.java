package com.book.search.net.naver;

import com.book.search.net.BookFeignClientConfiguration;
import com.book.search.net.naver.model.NaverBooksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 카카오 책 검색 수행 클래스
 */
@FeignClient(value = "naver-store",
        url = "https://openapi.naver.com",
        configuration = {BookFeignClientConfiguration.class},
        fallbackFactory = NaverSearchBookClientFallback.class)
public interface NaverSearchBookClient {
    @GetMapping(value = "/v1/search/book.json")
    NaverBooksResponse request(@RequestHeader("X-Naver-Client-Id") String clientId,
                               @RequestHeader("X-Naver-Client-Secret") String secret,
                               @RequestParam(value = "query") String query,
                               @RequestParam(value = "start") int start,
                               @RequestParam(value = "display") int display,
                               @RequestParam(value = "sort") String sort,
                               @RequestParam(value = "target") String target);
}