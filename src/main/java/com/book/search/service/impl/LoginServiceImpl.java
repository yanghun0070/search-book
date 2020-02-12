package com.book.search.service.impl;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.data.UserData;
import com.book.search.common.security.MemberDetailsService;
import com.book.search.component.TokenComponent;
import com.book.search.endpoint.model.request.LoginRequest;
import com.book.search.endpoint.model.response.LoginResponse;
import com.book.search.exception.http.ErrorException;
import com.book.search.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private MemberDetailsService memberDetailsService;
    private PasswordEncoder passwordEncoder;
    private TokenComponent tokenComponent;

    @Autowired
    public LoginServiceImpl(MemberDetailsService memberDetailsService,
                            PasswordEncoder passwordEncoder,
                            TokenComponent tokenComponent) {
        this.memberDetailsService = memberDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenComponent = tokenComponent;
    }

    public LoginResponse run(LoginRequest request) {

        UserDetails user;
        try {
            user = memberDetailsService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ErrorException(HttpStatus.BAD_REQUEST,
                    ErrorCode.NOT_FOUND_USER,
                    request.getUsername());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorCode.INCORRECT_PASSWORD);

        return buildResponse(user.getUsername(), user.getAuthorities(), tokenComponent.generateToken((UserData) user));
    }

    private LoginResponse buildResponse(String username, Collection<? extends GrantedAuthority> authorities, String token) {
        return LoginResponse.builder()
                .username(username)
                .authorities(authorities)
                .token(token)
                .build();
    }

}
