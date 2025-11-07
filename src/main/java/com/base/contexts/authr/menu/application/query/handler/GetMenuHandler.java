package com.base.contexts.authr.menu.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.contexts.authr.menu.application.query.mapper.MenuQueryMapper;
import com.base.contexts.authr.menu.application.query.port.in.GetMenuUseCase;
import com.base.contexts.authr.menu.domain.port.out.MenuPermissionMapQueryPort;
import com.base.contexts.authr.menu.domain.port.out.MenuQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetMenuHandler implements GetMenuUseCase {

    private final MenuQueryPort menuRepository;
    private final MenuPermissionMapQueryPort menuPermissionQueryPort;
    private final MenuQueryMapper menuQueryMapper;

    @Override
    public MenuQueryResult handle(Long menuId) {
        return menuRepository.findById(menuId)
                .map(menu -> menuQueryMapper.toQueryResult(
                        menu,
                        menuPermissionQueryPort.findPermissionIdsByMenuId(menuId)
                ))
                .orElseThrow(() -> new NotFoundException("Menu not found"));
    }
}
