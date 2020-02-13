package com.book.search.endpoint.model.request;

import com.book.search.common.code.BookStoreCode;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.data.StoreTranslate;
import com.book.search.net.data.StoreRequest;
import com.book.search.net.kakao.model.KakaoBookRequest;
import com.book.search.net.naver.model.NaverBookRequest;
import lombok.Data;

/**
 * 책 검색 요청데이터 정의
 */
@Data
public class SearchBookRequest implements StoreTranslate {
    private String query;
    private KeyTypeCode keyTypeCode;
    private SortCode sortCode;
    private int size;
    private int page;

    public SearchBookRequest(String query, KeyTypeCode keyTypeCode, String sort, int size, int page) {
        this.query = query;
        this.keyTypeCode = keyTypeCode;
        this.sortCode = sort == null ? SortCode.LASTEST : SortCode.resolveKeyType(sort);
        this.size = size;
        this.page = page;
    }

    @Override
    public StoreRequest translate(BookStoreCode storeCode) {
        switch (storeCode) {
            case NAVER:
                return NaverBookRequest.builder().
                        query(this.query).
                        sortCode(this.sortCode).
                        start(((this.page - 1) * this.size) + 1).
                        display(this.size).
                        keyTypeCode(this.keyTypeCode).
                        build();
            case KAKAO:
            default:
                return KakaoBookRequest.builder().
                        query(this.query).
                        sortCode(this.sortCode).
                        page(this.page).
                        size(this.size).
                        keyTypeCode(this.keyTypeCode).
                        build();
        }
    }
}
