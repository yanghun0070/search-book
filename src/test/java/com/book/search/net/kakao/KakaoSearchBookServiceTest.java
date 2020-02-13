package com.book.search.net.kakao;

import com.book.search.AbstractSearchTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.exception.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class KakaoSearchBookServiceTest extends AbstractSearchTest {

    @Autowired
    KakaoSearchBookService kakaoSearchBookService;

    @Test
    public void test_request() throws BusinessException {

        StoreTranslate request = new SearchBookRequest("엄마", KeyTypeCode.DEFAULT, SortCode.LASTEST.getCode(), 1, 5);
        BookResultData response =
                kakaoSearchBookService.
                        doHandling(request);

        System.out.println("responseBody : " + response);

        assertNotNull(response);

    }
}