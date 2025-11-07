package com.base.contexts.authr.userrolemap.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.userrolemap.domain.model.UserRoleMap;
import com.base.contexts.authr.userrolemap.domain.model.UserRoleMapId;

public interface UserRoleMapQueryPort {

    Optional<UserRoleMap> findById(UserRoleMapId id);

    List<UserRoleMap> findByUserId(Long userId);

    List<Long> findRoleIdsByUserId(Long userId);

    List<Long> findUserIdsByRoleIds(Collection<Long> roleIds);
}
