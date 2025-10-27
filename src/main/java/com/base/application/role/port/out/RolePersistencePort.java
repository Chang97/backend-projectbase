package com.base.application.role.port.out;

import java.util.List;
import java.util.Optional;

import com.base.application.role.usecase.query.condition.RoleSearchCondition;
import com.base.domain.role.Role;

public interface RolePersistencePort {

    Role save(Role role);

    Optional<Role> findById(Long roleId);

    boolean existsByRoleName(String roleName);

    List<Role> search(RoleSearchCondition condition);
}
