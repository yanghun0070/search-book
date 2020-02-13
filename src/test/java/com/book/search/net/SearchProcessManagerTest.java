package com.book.search.net;


import com.book.search.AbstractSearchTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.data.StoreTranslate;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.exception.biz.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchProcessManagerTest extends AbstractSearchTest {

    @Autowired
    SearchProcessManager searchProcessManager;

    @Test(expected = NotFoundException.class)
    public void test_chain_request() throws NotFoundException {
        StoreTranslate request = new SearchBookRequest(
                "qmfkdfsafdabdsfgwegasdfsfsdfsdfsdfsdfdsfsdsdfdsdfdfsf",
                KeyTypeCode.DEFAULT, SortCode.LASTEST.getCode(), 1, 5);

        searchProcessManager
                .makeRequest(request);

    }

}