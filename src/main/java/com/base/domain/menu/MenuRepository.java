package com.base.domain.menu;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuCode(String menuCode);
    Boolean existsByMenuCode(String menuCode);
    List<Menu> findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(Long upperMenuId);
    List<Menu> findByUpperMenu_MenuAndUseYnTrueOrderBySrtAsc(String upperMenu);
}
