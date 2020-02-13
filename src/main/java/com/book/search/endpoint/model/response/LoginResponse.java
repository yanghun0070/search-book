package com.book.search.endpoint.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 로그인 결과 데이터 정의
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginResponse {
    private String username;
    private Collection authorities;
    private String token;
}
