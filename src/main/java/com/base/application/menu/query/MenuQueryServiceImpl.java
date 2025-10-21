package com.base.application.menu.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.menu.dto.MenuResponse;
import com.base.domain.mapping.MenuPermissionMapRepository;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuRepository menuRepository;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final MenuResponseAssembler menuResponseAssembler;

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenus(MenuSearchCondition condition) {
        if (condition != null) {
            condition.normalize();
        }
        var menus = menuRepository.findAll(MenuSpecifications.withCondition(condition));
        return buildMenuResponses(menus);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuResponse getMenu(Long id) {
        var menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        return menuResponseAssembler.assemble(menu, menuPermissionMapRepository.findPermissionIdsByMenuId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByUpperId(Long upperMenuId) {
        var menus = menuRepository.findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(upperMenuId);
        return buildMenuResponses(menus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByUpperMenu(String upperMenu) {
        var menus = menuRepository.findByUpperMenu_MenuCodeAndUseYnTrueOrderBySrtAsc(upperMenu);
        return buildMenuResponses(menus);
    }

    private List<MenuResponse> buildMenuResponses(List<com.base.domain.menu.Menu> menus) {
        if (menus.isEmpty()) {
            return List.of();
        }
        var permissionMap = menuPermissionMapRepository.findPermissionMappings(
                menus.stream().map(com.base.domain.menu.Menu::getMenuId).toList()).stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        row -> (Long) row[0],
                        java.util.stream.Collectors.mapping(row -> (Long) row[1], java.util.stream.Collectors.toList())));

        return menus.stream()
                .sorted(java.util.Comparator.comparing(menuResponseAssembler::buildOrderKey))
                .map(menu -> menuResponseAssembler.assemble(menu, permissionMap.getOrDefault(menu.getMenuId(), List.of())))
                .toList();
    }
}
