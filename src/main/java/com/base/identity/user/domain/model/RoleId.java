package com.base.identity.user.domain.model;

public record RoleId(Long value) {
    public static RoleId of(Long value) { 
        return new RoleId(value); 
    }
}
