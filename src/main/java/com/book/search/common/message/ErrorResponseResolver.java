package com.book.search.common.message;

import com.book.search.common.code.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 에러코드에 매핑되는 resource message 을 통해 ErrorResponse 객체를 새생성하는 클래이다.
 *
 */
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
