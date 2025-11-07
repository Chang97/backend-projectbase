package com.base.contexts.authr.menu.application.command.support;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;
import com.base.contexts.authr.menu.domain.port.out.MenuPermissionMapCommandPort;
import com.base.contexts.authr.permission.domain.port.out.PermissionCommandPort;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuPermissionSynchronizer {

    private final PermissionCommandPort permissionCommandPort;
    private final MenuPermissionMapCommandPort menuPermissionCommandPort;

    public void sync(Long menuId, List<Long> permissionIds) {
        List<Long> sanitized = sanitize(permissionIds);
        if (sanitized.isEmpty()) {
            menuPermissionCommandPort.replacePermissions(menuId, List.of());
            return;
        }

        validatePermissionsExist(sanitized);
        menuPermissionCommandPort.replacePermissions(menuId, buildMappings(menuId, sanitized));
    }

    private List<Long> sanitize(List<Long> permissionIds) {
        return permissionIds == null
                ? List.of()
                : permissionIds.stream()
                        .filter(java.util.Objects::nonNull)
                        .distinct()
                        .toList();
    }

    private void validatePermissionsExist(List<Long> permissionIds) {
        long found = permissionCommandPort.findAllByIds(permissionIds).stream()
                .map(permission -> permission.getPermissionId().permissionId())
                .filter(java.util.Objects::nonNull)
                .count();
        if (found != permissionIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }
    }

    private List<MenuPermissionMap> buildMappings(Long menuId, List<Long> permissionIds) {
        return permissionIds.stream()
                .map(permissionId -> MenuPermissionMap.of(menuId, permissionId))
                .toList();
    }
}
