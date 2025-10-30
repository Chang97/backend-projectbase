package com.base.contexts.authr.application.dto;

import java.util.List;

public record UserMenuAccessResult(
    List<MenuTreeResult> menuTree,
    List<MenuResult> flatMenus
) {}
