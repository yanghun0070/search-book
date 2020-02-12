package com.book.search.common.security;

import com.book.search.common.code.Role;
import com.book.search.common.data.UserData;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class JWTClaimsBuilderTest {

    @Test
    public void test_build() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        UserData claim = new UserData();
        claim.setMemberId(1L);
        claim.setUsername("testuser");
        claim.setPassword("1234");
        claim.setAuthorities(grantedAuthorities);

        PlainJWT jwt = buildJwt(claim, grantedAuthorities);

        System.out.println(jwt.serialize());

    }

    public PlainJWT buildJwt(UserDetails claim, Set<GrantedAuthority> grantedAuthorities) {
        return new PlainJWT(new JWTClaimsSet.Builder().
                jwtID(UUID.randomUUID().toString()).
                subject(claim.getUsername()).
                issueTime(new Date()).
                claim("scope", grantedAuthorities.
                        stream().
                        map(GrantedAuthority::getAuthority).
                        collect(Collectors.toList())).
                build());

    }

    public PlainJWT deSerializeJwt(String str) throws ParseException {
        PlainJWT jwt = PlainJWT.parse(str);
        return jwt;
    }

}