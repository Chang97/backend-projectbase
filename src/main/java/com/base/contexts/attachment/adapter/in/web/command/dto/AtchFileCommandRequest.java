package com.base.contexts.attachment.adapter.in.web.command.dto;

import jakarta.validation.constraints.NotNull;

public record AtchFileCommandRequest(
        @NotNull Long fileGroupCodeId,
        Boolean useYn) {
}
