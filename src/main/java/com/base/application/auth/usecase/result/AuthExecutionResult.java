package com.base.application.auth.usecase.result;

import java.util.List;

import org.springframework.http.ResponseCookie;

public record AuthExecutionResult(
        AuthSession session,
        List<ResponseCookie> cookies
) {
}
