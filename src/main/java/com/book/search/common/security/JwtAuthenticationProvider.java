package com.book.search.common.security;

import com.book.search.common.data.UserData;
import com.book.search.common.data.auditor.AuditHelper;
import com.book.search.component.MemberComponent;
import com.book.search.component.TokenComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT 안중 token을 검증하는 클래스이다.
 */
@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    TokenComponent tokenComponent;

    @Autowired
    MemberComponent memberComponent;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "Authentication is missing");
        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication, "This method only accepts JwtAuthenticationToken");
        if (authentication.getPrincipal() instanceof UserData)
            return authentication;

        String jwtToken = (String) authentication.getPrincipal();
        if (jwtToken == null)
            throw new AuthenticationCredentialsNotFoundException("Authentication token is missing");

        UserData userData;
        try {
            userData = tokenComponent.verify(jwtToken);
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("token is not valid");
        } catch (JOSEException e) {
            log.error(e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("token is not verify");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new AuthenticationCredentialsNotFoundException("invalid jwt token");
        }

        if (userData.isExpired())
            throw new AuthenticationCredentialsNotFoundException("jwt token expired");

        try {
            memberComponent.loadMember(userData.getMemberId());
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("withdrawal");
        }

        log.debug("claimsSet:{}", userData.toString());
        List<GrantedAuthority> authorities = userData.getGrantedAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        AuditHelper.setCurrentAuditor(userData.getUsername());

        return issueAuthentication(userData, authorities);
    }

    private Authentication issueAuthentication(UserData userData, List<GrantedAuthority> authorities) {
        AbstractAuthenticationToken token = new JwtAuthenticationToken(userData, null, authorities);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
