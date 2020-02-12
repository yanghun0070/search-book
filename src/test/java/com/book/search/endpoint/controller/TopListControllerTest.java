package com.book.search.endpoint.controller;

import com.book.search.AbstractControllerTest;
import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.LoginRequest;
import org.junit.Test;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopListControllerTest extends AbstractControllerTest {

    private static LoginRequest getLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("test1234!");
        return request;
    }

    private String obtainToken() throws Exception {
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
    public void test_search_keyword() throws Exception {
        String token = obtainToken();

        mockMvc.perform(get(Endpoints.SEARCH_TOPS_ENDPOINT)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}