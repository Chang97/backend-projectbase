package com.base.shared.menu.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NotFoundException;
import com.base.shared.menu.application.query.dto.MenuQueryResult;
import com.base.shared.menu.application.query.mapper.MenuQueryMapper;
import com.base.shared.menu.application.query.port.in.GetMenuUseCase;
import com.base.shared.menu.domain.port.out.MenuRepository;
import com.base.shared.menupermissionmap.domain.port.out.MenuPermissionMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetMenuHandler implements GetMenuUseCase {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionRepository;
    private final MenuQueryMapper menuQueryMapper;

    @Override
    public MenuQueryResult handle(Long menuId) {
        return menuRepository.findById(menuId)
                .map(menu -> menuQueryMapper.toResult(
                        menu,
                        menuPermissionRepository.findPermissionIdsByMenuId(menuId)
                ))
                .orElseThrow(() -> new NotFoundException("Menu not found"));
    }
}
