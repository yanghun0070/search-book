package com.book.search.endpoint.model.request;

import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 로그인 요청 데이터 정의
 */
@Data
public class LoginRequest {
    @JsonProperty(value = SerializedFieldNames.USERNAME, required = true)
    private String username;
    @JsonProperty(value = SerializedFieldNames.PASSWORD, required = true)
    private String password;
}
