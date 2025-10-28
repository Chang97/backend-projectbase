package com.base.shared.menu.adapter.in.web.command.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record MenuCommandRequest(
        Long upperMenuId,
        @NotBlank(message = "메뉴 코드는 필수입니다.")
        String menuCode,
        @NotBlank(message = "메뉴명은 필수입니다.")
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<Long> permissionIds
) {}
