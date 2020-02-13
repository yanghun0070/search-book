package com.book.search.common.security;

import com.book.search.common.data.UserData;
import com.book.search.common.data.entity.Member;
import com.book.search.component.MemberComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 로그인 요청시 로그인 아이디에 매핑되는 회원정보를 제공하는 클래스이다.
 */
@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberComponent memberComponent;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberComponent.loadMember(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getValue()));

        return new UserData(member, grantedAuthorities);
    }
}