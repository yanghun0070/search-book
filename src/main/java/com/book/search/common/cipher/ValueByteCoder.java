package com.book.search.common.cipher;

import org.springframework.stereotype.Component;

@Component
public class ValueByteCoder {
    public String encode(byte[] bytes) {
        return new String(bytes);
    }

    public byte[] decode(String encoded) {
        return encoded.getBytes();
    }
}
