package com.base.contexts.authr.role.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.model.RoleFilter;

public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(Long roleId);

    Optional<Role> findByName(String roleName);

    boolean existsByName(String roleName);

    List<Role> findAllByIds(Collection<Long> roleIds);

    List<Role> search(RoleFilter filter);
}
