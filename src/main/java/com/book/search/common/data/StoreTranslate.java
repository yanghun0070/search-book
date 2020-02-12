package com.book.search.common.data;

import com.book.search.common.code.BookStoreCode;
import com.book.search.net.data.StoreRequest;

public interface StoreTranslate {
    StoreRequest translate(BookStoreCode storeCode);
}
