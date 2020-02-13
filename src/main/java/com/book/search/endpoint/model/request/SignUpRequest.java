package com.book.search.endpoint.model.request;

import com.book.search.common.code.Role;
import com.book.search.common.data.entity.Member;
import com.book.search.endpoint.SerializedFieldNames;
import com.book.search.exception.biz.InvaildRequestParameterException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@ToString(exclude = "password")
/**
 * 회원가입 데이터 정의
 */
@Data
public class SignUpRequest {
    @JsonProperty(value = SerializedFieldNames.NAME, required = true)
    private String name;
    @JsonProperty(value = SerializedFieldNames.LOGIN_ID, required = true)
    private String loginId;
    @JsonProperty(value = SerializedFieldNames.PASSWORD, required = true)
    private String password;

    public Member translate(PasswordEncoder passwordEncoder) throws InvaildRequestParameterException {
        if (StringUtils.isEmpty(getName()) || StringUtils.isEmpty(getLoginId()) || StringUtils.isEmpty(password)) {
            throw new InvaildRequestParameterException();
        }

        Member member = new Member();
        member.setLoginId(getLoginId());
        member.setUsername(getName());
        member.setPassword(passwordEncoder.encode(getPassword()));
        member.setRole(Role.MEMBER);
        return member;
    }
}
