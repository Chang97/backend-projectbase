package com.base.infra.persistence.user;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.role.port.out.UserRoleAssociationPort;
import com.base.domain.mapping.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUserRoleAssociationAdapter implements UserRoleAssociationPort {

    private final UserRoleMapRepository userRoleMapRepository;

    @Override
    public List<Long> findUserIdsByRoleIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        return userRoleMapRepository.findUserIdsByRoleIds(roleIds);
    }
}
