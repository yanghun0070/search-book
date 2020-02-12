package com.book.search.endpoint.model.request;

import com.book.search.endpoint.SerializedFieldNames;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty(value = SerializedFieldNames.USERNAME, required = true)
    private String username;
    @JsonProperty(value = SerializedFieldNames.PASSWORD, required = true)
    private String password;
}
