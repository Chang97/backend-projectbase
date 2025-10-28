package com.base.shared.userrolemap.domain.model;

public record UserRoleMapId(Long userId, Long roleId) {

    public static UserRoleMapId of(Long userId, Long roleId) {
        return new UserRoleMapId(userId, roleId);
    }
}
