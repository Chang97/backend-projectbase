package com.base.application.menu.usecase.query.detail;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.menu.usecase.query.assembler.MenuResultAssembler;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.domain.mapping.MenuPermissionMapRepository;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetMenuUseCaseImpl implements GetMenuUseCase {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final MenuResultAssembler menuResultAssembler;

    @Override
    public MenuResult handle(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        List<Long> permissionIds = menuPermissionMapRepository.findPermissionIdsByMenuId(menuId);
        return menuResultAssembler.toResult(menu, permissionIds);
    }
}
