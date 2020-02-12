package com.book.search.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -5112433518559349349L;

    @JsonProperty
    private String code;

    @JsonProperty
    private String message;
}
