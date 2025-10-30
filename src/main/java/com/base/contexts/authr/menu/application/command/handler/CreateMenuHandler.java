package com.base.contexts.authr.menu.application.command.handler;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.base.contexts.authr.menu.application.command.mapper.MenuCommandMapper;
import com.base.contexts.authr.menu.application.command.port.in.CreateMenuUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.port.out.MenuRepository;
import com.base.contexts.authr.menupermissionmap.domain.model.MenuPermissionMap;
import com.base.contexts.authr.menupermissionmap.domain.port.out.MenuPermissionMapRepository;
import com.base.contexts.authr.permission.domain.port.out.PermissionRepository;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateMenuHandler implements CreateMenuUseCase {

    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;
    private final MenuPermissionMapRepository menuPermissionRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final MenuCommandMapper menuCommandMapper;

    @Override
    public MenuCommandResult handle(MenuCommand command) {
        Menu menu = menuCommandMapper.toDomain(command);
        if (menuRepository.existsByMenuCode(menu.getMenuCode())) {
            throw new ConflictException("Menu code already exists: " + menu.getMenuCode());
        }

        Menu saved = menuRepository.save(menu);
        syncMenuPermissions(saved.getMenuId().value(), command.permissionIds());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
        return menuCommandMapper.toResult(saved);
    }

    private void syncMenuPermissions(Long menuId, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }
        List<Long> sanitized = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (sanitized.isEmpty()) {
            menuPermissionRepository.replacePermissions(menuId, List.of());
            return;
        }

        long count = permissionRepository.findAllByIds(sanitized).stream()
                .map(permission -> permission.getPermissionId().permissionId())
                .filter(Objects::nonNull)
                .count();
        if (count != sanitized.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        menuPermissionRepository.replacePermissions(menuId, sanitized.stream()
                .map(permissionId -> MenuPermissionMap.of(menuId, permissionId))
                .toList());
    }
}
