package com.base.contexts.authr.menu.domain.view;

import java.util.List;

public record MenuTree(
    Long menuId, 
    String menuCode, 
    String menuName, 
    String url, 
    List<MenuTree> children
) {}
