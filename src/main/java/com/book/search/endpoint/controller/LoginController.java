package com.book.search.endpoint.controller;

import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.LoginRequest;
import com.book.search.endpoint.model.response.LoginResponse;
import com.book.search.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(Endpoints.ACCOUNT_LOGIN)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return loginService.run(request);
    }
}