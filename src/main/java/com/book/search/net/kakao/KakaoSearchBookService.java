package com.book.search.net.kakao;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.data.BookResultData;
import com.book.search.common.data.StoreTranslate;
import com.book.search.common.properties.KakaoProperties;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.net.SearchProcess;
import com.book.search.net.data.StoreRequest;
import com.book.search.net.kakao.model.KakaoBooksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 카카오 책 검색 수행 클래스
 */
@Slf4j
@Component
public class KakaoSearchBookService extends SearchProcess {

    private KakaoProperties properties;
    private KakaoSearchBookClient kakaoSearchBookClient;
    private String authorization;

    @Autowired
    public KakaoSearchBookService(@Lazy KakaoSearchBookClient kakaoSearchBookClient,
                                  KakaoProperties properties) {
        this.kakaoSearchBookClient = kakaoSearchBookClient;
        this.properties = properties;
        initialize();
    }

    private void initialize() {
        authorization = "KakaoAK " + properties.getApiKey();
    }

    @Override
    public BookResultData doHandling(StoreTranslate value) throws NotFoundException {
        StoreRequest request = value.translate(BookStoreCode.KAKAO);
        KakaoBooksResponse response = kakaoSearchBookClient.request(authorization,
                request.getQuery(),
                request.getPage(),
                request.getSize(),
                request.getSortCode().getKakaoCode(),
                request.getKeyTypeCode().getKakaoCode());

        if (response.getBooks().size() == 0)
            throw new NotFoundException("no search result to kakao");

        return response;
    }
}
