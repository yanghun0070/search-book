package com.book.search.common.properties;


import com.book.search.AbstractSearchTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class BookPropertiesTest extends AbstractSearchTest {

    @Autowired
    private BookProperties bookProperties;

    @Test
    public void test_proerties() {
        assertEquals(bookProperties.getKeyPairPath(), "rsa_key/book-rsa-key.pem");

    }

}