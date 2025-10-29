package com.base.authn.adapter.in.web.dto;

import java.util.List;

import com.base.authr.adapter.in.web.dto.UserSummaryResponse;

public record LoginResponse(
        UserSummaryResponse user,
        List<String> permissions
) {
}
