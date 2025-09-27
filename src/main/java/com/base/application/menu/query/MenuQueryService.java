package com.base.application.menu.query;

import java.util.List;

import com.base.domain.menu.Menu;

public interface MenuQueryService {

    List<Menu> getMenus();
    Menu getMenu(Long id);

}
