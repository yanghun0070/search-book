package com.book.search.endpoint.controller;

import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.SignUpRequest;
import com.book.search.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SignUpController {
    @Autowired
    SignUpService signUpService;

    @PutMapping(Endpoints.ACCOUNT_SIGNUP)
    public void signUp(@Validated @RequestBody SignUpRequest request) throws Exception {
        log.debug("[SignUpController] request : {}", request);
        signUpService.run(request);
    }
}