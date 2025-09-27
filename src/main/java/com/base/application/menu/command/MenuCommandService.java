package com.base.application.menu.command;

import com.base.domain.menu.Menu;

public interface MenuCommandService {

    Menu createMenu(Menu code);
    Menu updateMenu(Long id, Menu code);
    void deleteMenu(Long id);
    
}
