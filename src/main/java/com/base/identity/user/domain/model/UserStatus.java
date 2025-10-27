package com.base.identity.user.domain.model;

public record UserStatus(Long codeId, String codeName) {

    public static UserStatus of(Long codeId, String codeName) {
        return new UserStatus(codeId, codeName);
    }

    public boolean isActive() { return "ACTIVE".equalsIgnoreCase(codeName); }
  }