package com.book.search.common.security;

import com.book.search.AbstractSearchTest;
import com.book.search.common.cipher.CipherKeyManager;
import com.book.search.common.cipher.RSASigner;
import com.book.search.common.data.UserData;
import com.book.search.common.code.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RSASignerTest extends AbstractSearchTest {

    @Autowired
    CipherKeyManager keyManager;

    public static UserData getUserData() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        UserData claim = new UserData();
        claim.setMemberId(1L);
        claim.setUsername("testuser");
        claim.setPassword("1234");
        claim.setGrantedAuthorities(grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return claim;
    }

    @Test
    public void test_protect() throws JOSEException, JsonProcessingException, ParseException {
        RSASigner rsaSigner = new RSASigner(keyManager);

        UserData claim = getUserData();
        String json = getObjectMapper().writeValueAsString(claim);
        String signJwt = rsaSigner.protect(json);

        String strJwt = rsaSigner.verify(signJwt);

        UserData user;
        user = getObjectMapper().readValue(strJwt, UserData.class);


        assertEquals(claim.getUsername(), user.getUsername());
        assertEquals(claim.getMemberId(), user.getMemberId());
        assertNull(user.getPassword());
    }
}