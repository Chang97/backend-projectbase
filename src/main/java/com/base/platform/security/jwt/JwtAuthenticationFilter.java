package com.base.platform.security.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;                  // 토큰 파싱/검증
    private final UserDetailsService userDetailsService;  // 사용자 로딩

    /**
     * 매 요청마다 Authorization 헤더에서 Bearer 토큰 추출 → 검증 → 컨텍스트 세팅.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {

            // 아직 인증 컨텍스트가 비어 있고 토큰이 유효한 경우만 인증 채움
            if (jwtService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String loginId = jwtService.extractLoginId(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        // 1) 우선 HttpOnly 쿠키에 실린 액세스 토큰을 찾는다.
        Cookie accessCookie = WebUtils.getCookie(request, "ACCESS_TOKEN");
        if (accessCookie != null && StringUtils.hasText(accessCookie.getValue())) {
            return accessCookie.getValue();
        }

        // 2) 과거 호환을 위해 Authorization 헤더도 허용한다.
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
