package com.book.search.net.naver;

import com.book.search.AbstractSearchTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.properties.NaverProperties;
import com.book.search.net.naver.model.NaverBooksResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NaverSearchBookClientTest extends AbstractSearchTest {

    @Autowired
    NaverSearchBookClient naverSearchBookClient;

    @Autowired
    NaverProperties properties;

    /**
     * @RequestHeader("X-Naver-Client-Id") String clientId,
     * @RequestHeader("X-Naver-Client-Secret") String secret,
     * @RequestParam(value = "query") String query,
     * @RequestParam(value = "start") int start,
     * @RequestParam(value = "display") int display,
     * @RequestParam(value = "sort") String sort,
     * @RequestParam(value = "target") String target);
     */

    @Test
    public void test_request() {
        NaverBooksResponse response = naverSearchBookClient.
                request(properties.getClientId(),
                        properties.getClientSecret(),
                        "엄마", 1, 10, SortCode.LASTEST.getNaverCode(),
                        KeyTypeCode.DEFAULT.getNaverCode());

        System.out.println("response : " + response);

    }

}