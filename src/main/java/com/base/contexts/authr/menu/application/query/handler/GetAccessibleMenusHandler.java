package com.base.contexts.authr.menu.application.query.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.contexts.authr.menu.application.query.dto.MenuTreeResult;
import com.base.contexts.authr.menu.application.query.dto.UserMenuAccessResult;
import com.base.contexts.authr.menu.application.query.mapper.MenuQueryMapper;
import com.base.contexts.authr.menu.application.query.port.in.GetAccessibleMenusUseCase;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.port.out.MenuRepository;
import com.base.contexts.authr.menu.domain.service.MenuViewBuilder;
import com.base.contexts.authr.menu.domain.view.MenuViews;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAccessibleMenusHandler implements GetAccessibleMenusUseCase {

    private final MenuRepository menuRepository;
    private final MenuQueryMapper menuQueryMapper;

    @Override
    public UserMenuAccessResult handle(Long userId) {
        List<Menu> accessibleMenus = menuRepository.findAccessibleMenusByUserId(userId);
        if (accessibleMenus.isEmpty()) {
            return new UserMenuAccessResult(List.of(), List.of());
        }

        // 비활성 제외 + Map으로 정규화
        var map = accessibleMenus.stream()
            .filter(m -> Boolean.TRUE.equals(m.getUseYn()))
            .collect(Collectors.toMap(
                m -> m.getMenuId().value(), m -> m, (a,b)->a, LinkedHashMap::new));

        // 도메인 서비스
        MenuViews views = MenuViewBuilder.build(map);

        List<MenuTreeResult> treeDto = views.tree().stream().map(menuQueryMapper::toMenuTreeResult).toList();
        List<MenuQueryResult> flatDto = views.flat().stream().map(menuQueryMapper::toMenuResult).toList();
        
        return new UserMenuAccessResult(treeDto, flatDto);
    }

}
