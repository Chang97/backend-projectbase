package com.base.contexts.identity.auth.adapter.in.web.dto;

import java.util.List;

public record LoginResponse(
        UserSummaryResponse user,
        List<String> permissions
) {
}
