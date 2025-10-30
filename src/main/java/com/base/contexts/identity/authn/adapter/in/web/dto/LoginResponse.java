package com.base.contexts.identity.authn.adapter.in.web.dto;

import java.util.List;

public record LoginResponse(
        UserSummaryResponse user,
        List<String> permissions
) {
}
