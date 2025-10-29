package com.base.authr.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.authr.adapter.in.web.dto.MenuTreeResponse;
import com.base.authr.application.dto.MenuTreeResult;

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
}
