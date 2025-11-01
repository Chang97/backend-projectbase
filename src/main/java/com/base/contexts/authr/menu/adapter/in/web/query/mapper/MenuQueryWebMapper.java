package com.base.contexts.authr.menu.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.adapter.in.web.query.dto.MenuQueryRequest;
import com.base.contexts.authr.menu.adapter.in.web.query.dto.MenuQueryResponse;
import com.base.contexts.authr.menu.adapter.in.web.query.dto.MenuTreeResponse;
import com.base.contexts.authr.menu.adapter.in.web.query.dto.UserMenuAccessResponse;
import com.base.contexts.authr.menu.application.query.dto.MenuQuery;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.contexts.authr.menu.application.query.dto.MenuTreeResult;
import com.base.contexts.authr.menu.application.query.dto.UserMenuAccessResult;
import com.base.shared.core.util.StringNormalizer;

@Component
public class MenuQueryWebMapper {

    public MenuQuery toQuery(MenuQueryRequest request) {
        if (request == null) {
            return new MenuQuery(null, null, null, null, null);
        }
        return new MenuQuery(
                request.menuId(),
                request.upperMenuId(),
                StringNormalizer.trimToNull(request.menuCode()),
                StringNormalizer.trimToNull(request.menuName()),
                request.useYn()
        );
    }

    public MenuQueryResponse toResponse(MenuQueryResult node) {
        return new MenuQueryResponse(
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

    public List<MenuQueryResponse> toResponses(List<MenuQueryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

        public List<MenuTreeResponse> toMenuTreeResponses(List<MenuTreeResult> nodes) {
        return nodes == null ? List.of() : nodes.stream()
                .map(this::toMenuTreeResponse)
                .toList();
    }

    private MenuTreeResponse toMenuTreeResponse(MenuTreeResult node) {
        if (node == null) {
            return null;
        }
        var children = toMenuTreeResponses(node.children());
        return new MenuTreeResponse(
                node.menuId(),
                node.upperMenuId(),
                node.menuCode(),
                node.menuName(),
                node.menuCn(),
                node.url(),
                node.srt(),
                node.useYn(),
                node.lvl(),
                children
        );
    }

    public UserMenuAccessResponse toUserMenuAccessResponse(UserMenuAccessResult result) {
        List<MenuQueryResult> flatMenus = result.flatMenus();
        List<MenuTreeResult> menuTree = result.menuTree();
        
        List<MenuQueryResponse> accessibleMenus = flatMenus.stream().map(this::toResponse).toList();
        List<MenuTreeResponse> menus = menuTree.stream().map(this::toMenuTreeResponse).toList();

        return new UserMenuAccessResponse(menus, accessibleMenus);
    }
}
