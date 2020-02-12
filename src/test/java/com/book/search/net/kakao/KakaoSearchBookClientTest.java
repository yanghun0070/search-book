package com.book.search.net.kakao;

import com.book.search.AbstractSearchTest;
import com.book.search.common.data.BookResultData;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.exception.BusinessException;
import com.book.search.net.kakao.model.KakaoBookRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class KakaoSearchBookClientTest extends AbstractSearchTest {

    @Autowired
    KakaoSearchBookClient kakaoSearchBookClient;

    /**
     * private String query;    //검색을 원하는 질의어	O	String
     * private SortCode sortCode; //결과 문서 정렬 방식	X (accuracy)	accuracy (정확도순) or latest (최신순)
     * private int page;    // 결과 페이지 번호	X(기본 1)	1-100 사이 Integer
     * private int size;    //한 페이지에 보여질 문서의 개수	X(기본 10)	1-50 사이 Integer
     * private KeyTypeCode keyTypeCode;
     *
     * @throws BusinessException
     */
    @Test
    public void test_request() throws BusinessException {

        KakaoBookRequest kakaoBookRequest = new KakaoBookRequest("엄마", SortCode.LASTEST, 1, 5, KeyTypeCode.DEFAULT);
        BookResultData response =
                kakaoSearchBookClient.
                        request(kakaoBookRequest);

        System.out.println("responseBody : " + response);

        assertNotNull(response);

    }
}