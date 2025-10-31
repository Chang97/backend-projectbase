package com.base.contexts.authr.menu.application.command.handler;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.base.contexts.authr.menu.application.command.mapper.MenuCommandMapper;
import com.base.contexts.authr.menu.application.command.port.in.UpdateMenuUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuId;
import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;
import com.base.contexts.authr.menu.domain.port.out.MenuPermissionMapRepository;
import com.base.contexts.authr.menu.domain.port.out.MenuRepository;
import com.base.contexts.authr.permission.domain.port.out.PermissionRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateMenuHandler implements UpdateMenuUseCase {

    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;
    private final MenuPermissionMapRepository menuPermissionRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final MenuCommandMapper menuCommandMapper;

    @Override
    public MenuCommandResult handle(Long menuId, MenuCommand command) {
        Menu existing = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        if (command.menuCode() != null
                && !command.menuCode().equals(existing.getMenuCode())
                && menuRepository.existsByMenuCode(command.menuCode())) {
            throw new ConflictException("Menu code already exists: " + command.menuCode());
        }

        menuCommandMapper.apply(existing, command);
        Menu saved = menuRepository.save(existing);
        syncMenuPermissions(saved.getMenuId(), command.permissionIds());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
        return menuCommandMapper.toResult(saved);
    }

    private void syncMenuPermissions(MenuId menuId, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }

        List<Long> sanitized = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (sanitized.isEmpty()) {
            menuPermissionRepository.replacePermissions(menuId.value(), List.of());
            return;
        }

        long count = permissionRepository.findAllByIds(sanitized).stream()
                .map(permission -> permission.getPermissionId().permissionId())
                .filter(Objects::nonNull)
                .count();
        if (count != sanitized.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        menuPermissionRepository.replacePermissions(menuId.value(), sanitized.stream()
                .map(permissionId -> MenuPermissionMap.of(menuId.value(), permissionId))
                .toList());
    }
}
