package com.base.api.role.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
    @NotBlank(message="역할명은 필수입니다.")
    String roleName,
    Boolean useYn
) {}
