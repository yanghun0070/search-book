package com.book.search.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 응답 에러메시지 구조를 정의한 클래스이다.
 */
@Data
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -5112433518559349349L;

    @JsonProperty
    private String code;

    @JsonProperty
    private String message;
}
