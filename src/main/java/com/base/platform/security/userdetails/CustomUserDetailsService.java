package com.base.platform.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.base.contexts.identity.authn.application.UserAuthorityService;
import com.base.contexts.identity.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // 로그인 ID 기반 조회
    private final UserAuthorityService userAuthorityService;

    /**
     * 로그인/필터 검증 시 호출.
     * - loginId로 사용자 조회
     * - useYn=true 인 사용자만 허용
     * - 없으면 UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .filter(user -> Boolean.TRUE.equals(user.getUseYn()))
                .map(user -> {
                    Long userId = user.getUserId() != null ? user.getUserId().value() : null;
                    return UserPrincipal.from(user, userAuthorityService.loadAuthoritiesOrEmpty(userId));
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
