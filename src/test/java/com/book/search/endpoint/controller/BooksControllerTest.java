package com.book.search.endpoint.controller;

import com.book.search.AbstractControllerTest;
import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.properties.KakaoProperties;
import com.book.search.endpoint.Endpoints;
import com.book.search.endpoint.model.request.LoginRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BooksControllerTest extends AbstractControllerTest {

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
    public void test_search_keyword() throws Exception {

        String token = obtainToken();

        String keyType = KeyTypeCode.DEFAULT.getCode();
        String query = "엄마앞에서";

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
                .header("Authorization", "Bearer " + token)
                .param("query", query))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_search_title_page_size() throws Exception {
        String token = obtainToken();
        String keyType = KeyTypeCode.TITLE.getCode();
        String query = "엄마앞에서";

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
                .header("Authorization", "Bearer " + token)
                .param("query", query)
                .param("page", "2")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_search_publisher() throws Exception {

        String token = obtainToken();
        String keyType = KeyTypeCode.PUBLISHER.getCode();
        String query = "동아";

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
                .header("Authorization", "Bearer " + token)
                .param("query", query))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void test_search_query_is_null() throws Exception {
        String token = obtainToken();
        String keyType = KeyTypeCode.DEFAULT.getCode();

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
                        .header("Authorization", "Bearer " + token)
//                .param("query", query)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Autowired
    KakaoProperties kakaoProperties;

    /**
     * 영판 검색결과 카카오 없음
     * @throws Exception
     */
    @Test
    public void test_search_naver() throws Exception {
        String token = obtainToken();

        String keyType = KeyTypeCode.PUBLISHER.getCode();
        String query = "영판";

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
                .header("Authorization", "Bearer " + token)
                .param("query", query)
                .param("page", "2")
                .param("size", "4"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_search_no_session_error() throws Exception {
        String keyType = KeyTypeCode.DEFAULT.getCode();
        String query = "엄마앞에서";

        mockMvc.perform(get(Endpoints.SEARCH_BOOK_ENDPOINT, keyType)
//                .header("Authorization", "Bearer " + token)
                .param("query", query))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}