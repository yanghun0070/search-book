package com.book.search.common.util;

import java.util.Base64;

public class CodecUtils {

    public static Base64.Encoder getBase64Encoder() {
        return Base64.getEncoder();
    }

    public static Base64.Encoder getBase64EncoderWithoutPadding() {
        return getBase64Encoder().withoutPadding();
    }

    public static Base64.Decoder getBase64Decoder() {
        return Base64.getDecoder();
    }
}
