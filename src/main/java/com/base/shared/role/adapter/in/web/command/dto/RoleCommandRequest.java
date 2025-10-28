package com.base.shared.role.adapter.in.web.command.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record RoleCommandRequest(
        @NotBlank(message = "역할명은 필수입니다.")
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
