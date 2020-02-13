package com.book.search.net.kakao;

import com.book.search.AbstractSearchTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.properties.KakaoProperties;
import com.book.search.net.kakao.model.KakaoBooksResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class KakaoSearchBookClientTest extends AbstractSearchTest {

    @Autowired
    KakaoSearchBookClient kakaoSearchBookClient;

    @Autowired
    KakaoProperties kakaoProperties;


    /**
     * (@RequestHeader("Authorization") String authorization,
     *
     * @RequestParam(value = "query") String query,
     * @RequestParam(value = "page") int page,
     * @RequestParam(value = "size") int size,
     * @RequestParam(value = "sort") String sort,
     * @RequestParam(value = "target") String target);
     */

    @Test
    public void test_kakaoclient() {
        KakaoBooksResponse response = kakaoSearchBookClient.
                request("KakaoAK " + kakaoProperties.getApiKey(), "엄마", 1, 10, SortCode.LASTEST.getKakaoCode(),
                        KeyTypeCode.DEFAULT.getKakaoCode());

        System.out.println("response : " + response);

    }

}