package com.book.search.service;

import com.book.search.endpoint.model.request.LoginRequest;
import com.book.search.endpoint.model.response.LoginResponse;

public interface LoginService {
    LoginResponse run(LoginRequest request);
}
