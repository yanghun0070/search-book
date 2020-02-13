package com.book.search.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 ROLE을 정의한다.
 * 일반 MEMBER의 경우 ROLE_MEMBER의 등급을 부여받는다.
 */
@AllArgsConstructor
@Getter
public enum Role {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private String value;

}