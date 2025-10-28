package com.base.shared.userrolemap.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoleMap {

    private final UserRoleMapId id;
    private final Long userId;
    private final Long roleId;

    public static UserRoleMap of(Long userId, Long roleId) {
        UserRoleMapId id = UserRoleMapId.of(userId, roleId);
        return new UserRoleMap(id, userId, roleId);
    }
}
