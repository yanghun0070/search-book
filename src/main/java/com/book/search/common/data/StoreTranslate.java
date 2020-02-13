package com.book.search.common.data;

import com.book.search.common.code.BookStoreCode;
import com.book.search.net.data.StoreRequest;

/**
 * 개별 store의 요청데이 변환 처리를 정의한 인터페이스이다.
 */
public interface StoreTranslate {
    StoreRequest translate(BookStoreCode storeCode);
}
