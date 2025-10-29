package com.base.authn.application.usecase.result;

import java.util.List;

import org.springframework.http.ResponseCookie;

public record LogoutExecutionResult(
        List<ResponseCookie> cookies
) {
}
