package com.book.search.component;


import com.book.search.AbstractSearchTest;
import com.book.search.common.security.RSASignerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class TokenComponentTest extends AbstractSearchTest {

    @Autowired
    TokenComponent tokenComponent;

    @Test
    public void test_generate() throws Exception {
        assertNotNull(tokenComponent.generateToken(RSASignerTest.getUserData()));
    }

    @Test
    public void test_verify() throws Exception {
        String signJwt = tokenComponent.generateToken(RSASignerTest.getUserData());
        assertNotNull(tokenComponent.verify(signJwt));
    }

}