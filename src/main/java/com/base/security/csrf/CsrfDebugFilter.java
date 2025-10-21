package com.base.security.csrf;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CsrfDebugFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CsrfDebugFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api/")) {
            String header = request.getHeader("X-CSRF-TOKEN");
            String cookie = null;
            if (request.getCookies() != null) {
                for (var cookieItem : request.getCookies()) {
                    if ("XSRF-TOKEN".equals(cookieItem.getName())) {
                        cookie = cookieItem.getValue();
                        break;
                    }
                }
            }
            log.debug("CSRF debug [{} {}] header={}, cookie={}", request.getMethod(), request.getRequestURI(), header, cookie);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !logger.isDebugEnabled();
    }
}
