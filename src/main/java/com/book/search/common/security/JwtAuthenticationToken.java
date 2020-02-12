package com.book.search.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final transient Object principal;
    private final transient Object credentials;

    public JwtAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.credentials = null;
    }


    public JwtAuthenticationToken(Object principal, Object credentials, List<GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}