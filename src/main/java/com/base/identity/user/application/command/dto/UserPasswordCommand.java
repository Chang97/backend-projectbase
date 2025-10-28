package com.base.identity.user.application.command.dto;

public record UserPasswordCommand(Long userId, String rawPassword) {
}
