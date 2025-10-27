package com.base.application.menu.usecase.query.list;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.menu.usecase.query.assembler.MenuResultAssembler;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.domain.mapping.MenuPermissionMapRepository;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetMenusByUpperIdUseCaseImpl implements GetMenusByUpperIdUseCase {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final MenuResultAssembler menuResultAssembler;

    @Override
    public List<MenuResult> handle(Long upperMenuId) {
        List<Menu> menus = menuRepository.findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(upperMenuId);
        if (menus.isEmpty()) {
            return List.of();
        }

        Map<Long, List<Long>> permissionMap = menuPermissionMapRepository
                .findPermissionMappings(menus.stream().map(Menu::getMenuId).toList())
                .stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(row -> (Long) row[1], Collectors.toList())
                ));

        return menus.stream()
                .map(menu -> menuResultAssembler.toResult(
                        menu,
                        permissionMap.getOrDefault(menu.getMenuId(), List.of()))
                )
                .toList();
    }
}
