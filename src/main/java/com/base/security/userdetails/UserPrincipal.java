package com.base.security.userdetails;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.base.domain.user.User;

/**
 * 스프링 시큐리티가 쓰는 사용자 어댑터.
 * - 권한은 현재 비워둠(역할 기반 인가 필요 시 채워넣기)
 */
public class UserPrincipal implements UserDetails {

    private final Long id;            // 사용자 PK
    private final String loginId;     // 로그인 ID
    private final String password;    // 해시된 비밀번호
    private final boolean enabled;    // 사용 여부

    public UserPrincipal(Long id, String loginId, String password, boolean enabled) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.enabled = enabled;
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(
                user.getUserId(),
                user.getLoginId(),
                user.getUserPassword(),
                Boolean.TRUE.equals(user.getUseYn())
        );
    }

    public Long getId() {
        return id;
    }

    // 현재 권한 비어 있음. 필요 시 ROLE_* 목록으로 교체.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
