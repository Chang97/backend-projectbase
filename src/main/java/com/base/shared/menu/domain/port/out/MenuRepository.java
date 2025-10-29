package com.base.shared.menu.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.shared.menu.domain.model.Menu;
import com.base.shared.menu.domain.model.MenuFilter;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(Long menuId);

    Optional<Menu> findByMenuCode(String menuCode);

    boolean existsByMenuCode(String menuCode);

    List<Menu> findByUpperMenuId(Long upperMenuId);

    List<Menu> search(MenuFilter filter);

    List<Menu> findAccessibleMenusByUserId(Long userId);
}
