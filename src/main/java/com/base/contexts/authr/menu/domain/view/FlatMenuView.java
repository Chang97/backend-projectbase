package com.base.contexts.authr.menu.domain.view;

public record FlatMenuView(
    Long menuId, 
    Long upperMenuId, 
    String menuCode, 
    String menuName, 
    String url,
    Integer srt, 
    boolean useYn, 
    int depth, 
    String path
) {}