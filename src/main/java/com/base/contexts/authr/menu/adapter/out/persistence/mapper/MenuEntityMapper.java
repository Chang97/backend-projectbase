package com.base.contexts.authr.menu.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuId;

@Component
public class MenuEntityMapper {

    public MenuEntity toNewEntity(Menu menu) {
        MenuEntity entity = new MenuEntity();
        apply(menu, entity);
        return entity;
    }

    public void merge(Menu menu, MenuEntity target) {
        apply(menu, target);
    }

    private void apply(Menu menu, MenuEntity target) {
        target.setMenuCode(menu.getMenuCode());
        target.setMenuName(menu.getMenuName());
        target.setMenuCn(menu.getMenuCn());
        target.setUrl(menu.getUrl());
        target.setSrt(menu.getSrt());
        target.setUseYn(Boolean.TRUE.equals(menu.getUseYn()));
    }

    public Menu toDomain(MenuEntity entity) {
        return Menu.restore(
                MenuId.of(entity.getMenuId()),
                entity.getUpperMenu() != null ? MenuId.of(entity.getUpperMenu().getMenuId()) : null,
                entity.getMenuCode(),
                entity.getMenuName(),
                entity.getMenuCn(),
                entity.getUrl(),
                entity.getSrt(),
                entity.getUseYn()
        );
    }
}
