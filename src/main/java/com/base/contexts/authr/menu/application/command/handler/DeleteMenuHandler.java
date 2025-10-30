package com.base.contexts.authr.menu.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.menu.application.command.port.in.DeleteMenuUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.port.out.MenuRepository;
import com.base.contexts.authr.menupermissionmap.domain.port.out.MenuPermissionMapRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteMenuHandler implements DeleteMenuUseCase {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long menuId) {
        Menu existing = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        existing.disable();
        menuRepository.save(existing);
        menuPermissionRepository.replacePermissions(menuId, List.of());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
    }
}
