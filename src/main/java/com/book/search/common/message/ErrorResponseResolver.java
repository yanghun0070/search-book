package com.book.search.common.message;

import com.book.search.common.code.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseResolver {

    private final MessageResolver messageResolver;

    @Autowired
    private ErrorResponseResolver(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public ErrorResponse getErrorResponse(ErrorResponse response, ErrorCode code, Object... args) {
        response.setCode(code.getCode());
        response.setMessage(messageResolver.getMessage(code, args));
        return response;
    }

    public ErrorResponse getErrorResponse(ErrorCode code, Object... args) {
        return getErrorResponse(new ErrorResponse(), code, args);
    }
}
