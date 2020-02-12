package com.book.search.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Proxy-Authorization Bearer {jwt token}
 * Authorization Bearer {access token}
 */
@Slf4j
public class JwtTokenExtractor {

    public Authentication extract(HttpServletRequest request) throws AuthenticationException {
        String tokenValue = extractToken(request);
        if (tokenValue != null) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(tokenValue);
            return authentication;
        }
        return null;
    }

    protected String extractToken(HttpServletRequest request) throws AuthenticationException {
        String token = extractHeaderToken(request);
        if (token == null) {
            throw new AuthenticationException("token is null"){
            };
        }

        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.startsWith("Bearer"))) {
                String authHeaderValue = value.substring("Bearer".length()).trim();
                return authHeaderValue;
            }
        }

        return null;
    }

}