package com.book.search.common.security;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.message.ErrorResponseResolver;
import com.book.search.common.properties.SearchBookConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final ErrorResponseResolver errorResponseResolver;
    private final ObjectMapper mapper;

    @Autowired
    public MemberAuthenticationEntryPoint(ObjectMapper mapper, ErrorResponseResolver errorResponseResolver) {
        this.mapper = mapper;
        this.errorResponseResolver = errorResponseResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
            IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mapper.writeValue(response.getWriter(),
                errorResponseResolver.getErrorResponse(ErrorCode.REQUIRED_LOGIN));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(SearchBookConstants.REALM_NAME);
    }
}