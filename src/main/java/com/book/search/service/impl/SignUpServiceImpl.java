package com.book.search.service.impl;

import com.book.search.common.code.ErrorCode;
import com.book.search.component.MemberComponent;
import com.book.search.endpoint.model.request.SignUpRequest;
import com.book.search.exception.biz.DuplicateMemberException;
import com.book.search.exception.biz.InvaildRequestParameterException;
import com.book.search.exception.http.ErrorException;
import com.book.search.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SignUpServiceImpl implements SignUpService {

    private MemberComponent memberComponent;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpServiceImpl(MemberComponent memberComponent,
                             PasswordEncoder passwordEncoder) {
        this.memberComponent = memberComponent;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(SignUpRequest request) {
        try {
            memberComponent.saveMember(request.translate(passwordEncoder));
        } catch (DuplicateMemberException e) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXIST, request.getLoginId());
        } catch (InvaildRequestParameterException e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST_INFO, request.toString());
        }
    }
}
