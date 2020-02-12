package com.book.search.common.code;

public enum ErrorCode {

    BAD_REQUEST("4000"), // Invalid request({0})
    REQUIRED_LOGIN("4001"), //Login is required.
    ALREADY_EXIST("4002"), //"{0}" already exists.
    INCORRECT_PASSWORD("4003"),        // Incorrect password.
    NOT_FOUND_USER("4004"),        // The user({0}) is not found.

    NOT_FOUND_RESOURCE("4013"),       // The resource({0}) is not found.
    INVALID_REQUEST_INFO("4014"),       // Invalid request information({0}).

    INTERNAL_ERROR("5999"),       // an unexpected error has occurred in the internal system.
    UNKNOWN("5001"),        // service unavailable.
    SERVICE_UNAVAILABLE("5003")        // service unavailable.
    ;

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
