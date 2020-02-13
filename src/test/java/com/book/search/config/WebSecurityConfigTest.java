package com.book.search.config;

import com.book.search.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebSecurityConfigTest extends AbstractControllerTest {

    @Test
    public void test_configure_private() throws Exception {
        this.mockMvc.perform(get("/search/qmfkqmfk")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    public void test_configure_public() throws Exception {
        this.mockMvc.perform(get("/account/qmfkqmfk")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test_encodePassworld() {

        String encode = passwordEncoder.encode("test1234!");

        System.out.println("######## endcode:" + encode);
        assertNotEquals(encode, "test1234!");
    }

}