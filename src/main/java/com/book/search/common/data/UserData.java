package com.book.search.common.data;

import com.book.search.common.data.entity.JwtData;
import com.book.search.common.data.entity.Member;
import com.book.search.common.properties.SearchBookConstants;
import com.book.search.common.util.CipherUtils;
import com.book.search.common.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@Data
public class UserData implements UserDetails, JwtData {
    private String jti;
    private long exp;

    private Long memberId;
    private String username;
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
    private List<String> grantedAuthorities;

    @JsonIgnore
    private String password;

    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public UserData(Member member, Collection<? extends GrantedAuthority> authorities) {
        this.jti = CipherUtils.generateSecret(256);
        this.exp = DateTimeUtils.getSecondsSinceEpochUTC() + SearchBookConstants.TOKEN_EXPIRE_SEC;
        this.memberId = member.getMemberId();
        this.username = member.getLoginId();
        this.authorities = authorities;
        this.grantedAuthorities = authorities.stream().map(GrantedAuthority::getAuthority).collect(toList());
        this.password = member.getPassword();
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isExpired() == false;
    }
}
