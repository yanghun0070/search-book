package com.book.search.endpoint.controller;


import com.book.search.AbstractControllerTest;
import com.book.search.common.data.entity.Member;
import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.SignUpRequest;
import com.book.search.repository.MemberRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignUpControllerTest extends AbstractControllerTest {

    @Autowired
    MemberRepository memberRepository;

    public static SignUpRequest getSignUpRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setLoginId("joinuser");
        request.setName("이동진");
        request.setPassword("1234test!");

        return request;
    }

    @Test
    public void test_login() throws Exception {
        SignUpRequest request = getSignUpRequest();

        mockMvc.perform(put(Endpoints.ACCOUNT_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isOk())
                .andDo(print());


        Optional<Member> member = memberRepository.findByLoginId(request.getLoginId());

        System.out.println(member);
        assertEquals(member.get().getLoginId(), request.getLoginId());

    }

    @Test
    public void test_login_dup() throws Exception {
        SignUpRequest request = getSignUpRequest();
        request.setLoginId("testuser");

        mockMvc.perform(put(Endpoints.ACCOUNT_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void test_login_not_null() throws Exception {
        SignUpRequest request = getSignUpRequest();
        request.setName(null);

        mockMvc.perform(put(Endpoints.ACCOUNT_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}