package com.base.contexts.identity.user.domain.port.out;

import java.util.List;

public interface UserRoleAssignmentPort {
    void replaceUserRoles(Long userId, List<Long> roleIds);
}
