package com.base.application.menu.command;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;

public interface MenuCommandService {

    MenuResponse createMenu(MenuRequest code);
    MenuResponse updateMenu(Long id, MenuRequest code);
    void deleteMenu(Long id);
    
}
