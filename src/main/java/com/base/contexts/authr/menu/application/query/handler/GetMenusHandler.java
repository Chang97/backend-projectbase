package com.base.contexts.authr.menu.application.query.handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.menu.application.query.dto.MenuQuery;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.contexts.authr.menu.application.query.mapper.MenuQueryMapper;
import com.base.contexts.authr.menu.application.query.port.in.GetMenusUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuFilter;
import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;
import com.base.contexts.authr.menu.domain.port.out.MenuPermissionMapRepository;
import com.base.contexts.authr.menu.domain.port.out.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetMenusHandler implements GetMenusUseCase {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionRepository;
    private final MenuQueryMapper menuQueryMapper;

    @Override
    public List<MenuQueryResult> handle(MenuQuery query) {
        MenuFilter filter = menuQueryMapper.toFilter(query);
        List<Menu> menus = menuRepository.search(filter);
        if (menus.isEmpty()) {
            return List.of();
        }
        List<Long> menuIds = menus.stream()
                .map(menu -> menu.getMenuId().value())
                .toList();
        Map<Long, List<Long>> permissionsByMenu = menuPermissionRepository.findByMenuIds(menuIds).stream()
                .collect(Collectors.groupingBy(
                        permission -> permission.getMenuId(),
                        Collectors.mapping(MenuPermissionMap::getPermissionId, Collectors.toList())
                ));
        return menus.stream()
                .map(menu -> menuQueryMapper.toQueryResult(
                        menu,
                        permissionsByMenu.getOrDefault(menu.getMenuId().value(), List.of())
                ))
                .toList();
    }
}
