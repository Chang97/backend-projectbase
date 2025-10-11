package com.base.application.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResponse;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.application.menu.query.UserMenuQueryService;
import com.base.domain.user.UserRepository;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;
import com.base.security.jwt.JwtService;
import com.base.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager; // 자격 증명 검증기
    private final JwtService jwtService;                       // JWT 발급/검증기
    private final UserRepository userRepository;               // 사용자 조회
    private final UserMapper userMapper;                       // 사용자 응답 변환
    private final UserMenuQueryService userMenuQueryService;   // 사용자별 메뉴 조회

    /**
     * 로그인 처리:
     * 1) AuthenticationManager로 인증
     * 2) 컨텍스트에 인증 저장
     * 3) JWT 발급
     * 4) 사용자 DTO 포함해 응답 구성
     */
    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(principal);
        UserResponse user = userRepository.findById(principal.getId())
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserMenuQueryService.UserMenuAccessResult menuAccess = userMenuQueryService.getAccessibleMenus(principal.getId());

        return new LoginResponse(
                accessToken,                        // 액세스 토큰
                "Bearer",                       // 토큰 타입
                jwtService.extractExpiration(accessToken), // 만료 시각(UTC)
                user,                           // 로그인 사용자 정보
                menuAccess.menuTree(),
                menuAccess.flatMenus()
        );
    }

    /**
     * 아이디/비밀번호로 인증 시도.
     * 실패 시 ValidationException으로 변환.
     */
    private Authentication authenticate(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.loginId(), request.password());
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            throw new ValidationException("Invalid login credentials.");
        }
    }
}
