package com.base.application.menu.query;

import java.util.List;

import com.base.api.menu.dto.MenuResponse;

public interface MenuQueryService {

    List<MenuResponse> getMenus();
    MenuResponse getMenu(Long id);
    List<MenuResponse> getMenusByUpperId(Long upperMenuId);
    List<MenuResponse> getMenusByUpperMenu(String upperMenu);

}
