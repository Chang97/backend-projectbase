package com.base.contexts.authr.menu.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.base.contexts.authr.menu.application.command.mapper.MenuCommandMapper;
import com.base.contexts.authr.menu.application.command.support.MenuPermissionSynchronizer;
import com.base.contexts.authr.menu.application.command.port.in.UpdateMenuUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuId;
import com.base.contexts.authr.menu.domain.policy.MenuPolicy;
import com.base.contexts.authr.menu.domain.port.out.MenuCommandPort;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateMenuHandler implements UpdateMenuUseCase {

    private final MenuCommandPort menuRepository;
    private final MenuPermissionSynchronizer menuPermissionSynchronizer;
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
        MenuPolicy.using(this::menuExists).assertUpperExists(existing.getUpperMenuId());
        Menu saved = menuRepository.save(existing);
        menuPermissionSynchronizer.sync(saved.getMenuId().value(), command.permissionIds());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
        return menuCommandMapper.toResult(saved);
    }

    private Boolean menuExists(MenuId menuId) {
        return menuId != null && menuRepository.existsById(menuId.value());
    }
}
