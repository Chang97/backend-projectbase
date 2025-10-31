package com.base.contexts.authr.menu.application.query.dto;

import java.util.List;

public record UserMenuAccessResult(
    List<MenuTreeResult> menuTree,
    List<MenuQueryResult> flatMenus
) {}
