package com.base.contexts.identity.user.application.command.dto;

public record UserPasswordCommand(Long userId, String rawPassword) {
}
