package com.base.application.auth.usecase.result;

import java.util.List;

import com.base.application.menu.usecase.result.MenuResult;
import com.base.application.menu.usecase.result.MenuTreeResult;
import com.base.application.user.usecase.result.UserResult;

public record AuthSession(
        UserResult user,
        List<MenuTreeResult> menuTree,
        List<MenuResult> accessibleMenus,
        List<String> permissions
) {
}
