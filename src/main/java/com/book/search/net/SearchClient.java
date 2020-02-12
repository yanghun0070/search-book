package com.book.search.net;

import com.book.search.net.data.StoreRequest;

public interface SearchClient<Res> {
    Res request(StoreRequest request);
}
