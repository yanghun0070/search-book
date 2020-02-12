package com.book.search.component;

import com.book.search.AbstractSearchTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.common.data.BookResultData;
import com.book.search.endpoint.model.request.SearchBookRequest;
import com.book.search.exception.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class BookComponentTest extends AbstractSearchTest {

    @Autowired
    BookComponent bookComponent;

    @Test
    public void loadBooks() throws BusinessException {

        BookResultData resultData = bookComponent
                .loadBooks(new SearchBookRequest(
                        "엄마",
                        KeyTypeCode.TITLE,
                        SortCode.ACCURACY.getCode(),
                        3,
                        1));

        System.out.println("resultData:"+resultData);


    }
}