package com.base.contexts.identity.authn.application.usecase.result;

import java.util.List;

public record AuthMenuTreeNode(
        Long menuId,
        String menuCode,
        String menuName,
        String url,
        List<AuthMenuTreeNode> children
) {
}
