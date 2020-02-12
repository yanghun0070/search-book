package com.book.search.common.message;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.properties.SearchBookConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@Slf4j
public class MessageResolver {

    private static final String DEFAULT_MESSAGE = "No message defined.";

    private MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(ErrorCode code, Object... args) {
        return getMessage(code.getCode(), SearchBookConstants.DEFAULT_LOCALE, args);
    }

    public String getMessage(String code, Locale locale, Object... args) {
        String message;

        try {
            message = messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException ex) {
            log.error(DEFAULT_MESSAGE, ex);
            message = DEFAULT_MESSAGE;
        }

        return message;
    }
}
