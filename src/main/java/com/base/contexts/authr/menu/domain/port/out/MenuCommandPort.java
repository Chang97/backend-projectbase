package com.base.contexts.authr.menu.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.menu.domain.model.Menu;

public interface MenuCommandPort {

    Menu save(Menu menu);

    Optional<Menu> findById(Long menuId);

    boolean existsByMenuCode(String menuCode);

    List<Menu> findByUpperMenuId(Long upperMenuId);
}

