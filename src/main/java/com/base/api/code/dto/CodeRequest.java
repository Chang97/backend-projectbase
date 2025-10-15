package com.base.api.code.dto;

import jakarta.validation.constraints.NotBlank;

public record CodeRequest(
        Long upperCodeId,
        @NotBlank(message = "코드는 필수 입력 항목입니다.")
        String code,
        @NotBlank(message = "코드명은 필수 입력 항목입니다.")
        String codeName,
        String description,
        Integer srt,
        String etc1,
        String etc2,
        String etc3,
        String etc4,
        Boolean useYn
) {}
