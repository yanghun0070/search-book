package com.book.search.common.util;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class CipherUtilsTest {

    @Test
    public void test_securet() {

        String str = CipherUtils.generateSecret(256);
        System.out.println("secure :" + str);

        byte[] bytes = Base64.getDecoder().decode(str);

        System.out.println("secure :" + bytes.length);
        assertTrue(bytes.length == (256 / 8));

    }

}