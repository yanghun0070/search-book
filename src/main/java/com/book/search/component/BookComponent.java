package com.book.search.component;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.data.BookResultData;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.exception.InternalException;
import com.book.search.net.kakao.KakaoSearchBookClient;
import com.book.search.net.naver.NaverSearchBookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public class BookComponent {

    private KakaoSearchBookClient kakaoSearchBookClient;
    private NaverSearchBookClient naverSearchBookClient;

    @Autowired
    public BookComponent(KakaoSearchBookClient kakaoSearchBookClient,
                         NaverSearchBookClient naverSearchBookClient) {
        this.kakaoSearchBookClient = kakaoSearchBookClient;
        this.naverSearchBookClient = naverSearchBookClient;
    }

    public BookResultData loadBooks(SearchBookRequest request) {
        try {
            return kakaoSearchBookClient.
                    request(request.translate(BookStoreCode.KAKAO));
        } catch (Exception e) {
            try {
                return naverSearchBookClient.
                        request(request.translate(BookStoreCode.NAVER));
            } catch (RestClientException ex) {
                throw new InternalException("resource server error");
            }
        }
    }
}
