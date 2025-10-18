package com.base.security.userdetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.base.domain.user.User;

/**
 * 스프링 시큐리티가 쓰는 사용자 어댑터.
 * - DB에서 로드한 역할/권한 정보를 그대로 보유한다.
 */
public class UserPrincipal implements UserDetails {

    private final Long id;            // 사용자 PK
    private final String loginId;     // 로그인 ID
    private final String password;    // 해시된 비밀번호
    private final boolean enabled;    // 사용 여부
    private final Set<GrantedAuthority> authorities;

    public UserPrincipal(Long id,
                         String loginId,
                         String password,
                         boolean enabled,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.enabled = enabled;
        if (authorities == null || authorities.isEmpty()) {
            this.authorities = Collections.emptySet();
        } else {
            Set<GrantedAuthority> resolved = authorities.stream()
                    .filter(authority -> authority != null && StringUtils.hasText(authority.getAuthority()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            this.authorities = Collections.unmodifiableSet(resolved);
        }
    }

    public static UserPrincipal from(User user, Collection<? extends GrantedAuthority> authorities) {
        return new UserPrincipal(
                user.getUserId(),
                user.getLoginId(),
                user.getUserPassword(),
                Boolean.TRUE.equals(user.getUseYn()),
                authorities
        );
    }

    @Deprecated(forRemoval = true)
    public static UserPrincipal from(User user) {
        return from(user, Collections.emptyList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
