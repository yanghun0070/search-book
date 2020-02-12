package com.book.search.endpoint.controller;

import com.book.search.AbstractControllerTest;
import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.LoginRequest;
import org.junit.Test;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends AbstractControllerTest {

//    public String

    public static LoginRequest getLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("test1234!");
        return request;
    }

    public String obtainToken() throws Exception {
        ResultActions result = mockMvc.perform(post(Endpoints.ACCOUNT_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(getLoginRequest()))
        )
                .andExpect(status().isOk())
                .andDo(print());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }

    @Test
    public void test_login() throws Exception {
        LoginRequest request = getLoginRequest();

        mockMvc.perform(post(Endpoints.ACCOUNT_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(request.getUsername()))
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }

    @Test
    public void test_login_not_found_id() throws Exception {
        LoginRequest request = getLoginRequest();
        request.setUsername("qmdfjskldajfdfsdfsd");
        mockMvc.perform(post(Endpoints.ACCOUNT_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void test_login_incorrect_pass() throws Exception {
        LoginRequest request = getLoginRequest();
        request.setPassword("qmdfjskldajfdfsdfsd");
        mockMvc.perform(post(Endpoints.ACCOUNT_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringFromObject(request))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}