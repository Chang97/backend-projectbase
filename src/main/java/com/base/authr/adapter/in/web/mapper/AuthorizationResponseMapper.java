package com.base.authr.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.authr.adapter.in.web.dto.MenuResponse;
import com.base.authr.adapter.in.web.dto.MenuTreeResponse;
import com.base.authr.adapter.in.web.dto.UserMenuAccessResponse;
import com.base.authr.application.dto.MenuResult;
import com.base.authr.application.dto.MenuTreeResult;
import com.base.authr.application.dto.UserMenuAccessResult;

@Component
public class AuthorizationResponseMapper {

    public List<MenuTreeResponse> toMenuTreeResponses(List<MenuTreeResult> nodes) {
        return nodes == null ? List.of() : nodes.stream()
                .map(this::toMenuTreeResponse)
                .toList();
    }

    private MenuTreeResponse toMenuTreeResponse(MenuTreeResult node) {
        if (node == null) return null;
        var children = toMenuTreeResponses(node.children());
        return new MenuTreeResponse(node.menuId(), node.menuCode(), node.menuName(), node.url(), children);
    }

    public MenuResponse toMenuResponse(MenuResult node) {
        return new MenuResponse(
            node.menuId(),
            node.menuCode(),
            node.upperMenuId(),
            node.menuName(),
            node.menuCn(),
            node.url(),
            node.srt(),
            node.useYn(),
            node.depth(),
            node.path(),
            node.permissionIds()
        );
    }

    public UserMenuAccessResponse toUserMenuAccessResponse(UserMenuAccessResult result) {
        List<MenuResult> flatMenus = result.flatMenus();
        List<MenuTreeResult> menuTree = result.menuTree();
        
        List<MenuResponse> accessibleMenus = flatMenus.stream().map(this::toMenuResponse).toList();
        List<MenuTreeResponse> menus = menuTree.stream().map(this::toMenuTreeResponse).toList();

        return new UserMenuAccessResponse(menus, accessibleMenus);
    }
}
