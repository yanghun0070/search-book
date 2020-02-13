package com.book.search.common.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 난수 생성을 위한 클래스이다.
 *  nonce값으 사용하지 않고 JWT ID를 난수 형태로 사용한다.
 */
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
