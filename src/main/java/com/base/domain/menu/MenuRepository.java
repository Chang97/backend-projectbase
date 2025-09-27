package com.base.domain.menu;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuCode(String menuCode);
}
