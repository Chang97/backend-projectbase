package com.base.authn.application.usecase.result;

import java.util.List;

public record AuthSession(
        AuthUserSnapshot user,
        List<AuthMenuTreeNode> menuTree,
        List<AuthMenuItem> accessibleMenus,
        List<String> permissions
) {
}
