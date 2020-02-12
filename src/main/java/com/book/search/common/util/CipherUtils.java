package com.book.search.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class CipherUtils {

    public static byte[] randomNumber(int bits) {
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[bits / 8];
        random.nextBytes(secret);
        return secret;
    }

    public static String generateSecret(int length) {
        if (length % 64 != 0)
            throw new IllegalArgumentException("invalid key length to generate secret.");

        return Base64.getEncoder().encodeToString(randomNumber(length));
    }

}
