package com.base.infra.persistence.permission;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.permission.port.out.PermissionLookupPort;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaPermissionLookupAdapter implements PermissionLookupPort {

    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAllByIds(Collection<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return List.of();
        }
        return permissionRepository.findAllById(permissionIds);
    }
}
