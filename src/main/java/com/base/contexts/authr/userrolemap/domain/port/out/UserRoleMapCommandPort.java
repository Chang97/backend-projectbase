package com.base.contexts.authr.userrolemap.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.userrolemap.domain.model.UserRoleMap;
import com.base.contexts.authr.userrolemap.domain.model.UserRoleMapId;

public interface UserRoleMapCommandPort {

    UserRoleMap save(UserRoleMap userRole);

    void saveAll(Collection<UserRoleMap> userRoles);

    void delete(UserRoleMapId id);

    void deleteAllByUserId(Long userId);

    void deleteAllByRoleId(Long roleId);

    Optional<UserRoleMap> findById(UserRoleMapId id);

    List<UserRoleMap> findByUserId(Long userId);

    List<Long> findRoleIdsByUserId(Long userId);

    List<Long> findUserIdsByRoleIds(Collection<Long> roleIds);
}
