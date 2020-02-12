package com.book.search.endpoint.model.request;

import org.junit.Assert;
import org.junit.Test;

public class SearchBookRequestTest {

    @Test
    public void test_pageToStart() {
        int i = ((1 - 1) * 10) + 1;

        Assert.assertEquals(i, 1);
    }
}