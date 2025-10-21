package com.base.application.menu.query;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.mapper.MenuMapper;
import com.base.domain.menu.Menu;

@Component
public class MenuResponseAssembler {

    private final MenuMapper menuMapper;

    public MenuResponseAssembler(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public MenuResponse assemble(Menu menu, List<Long> permissionIds) {
        MenuResponse base = menuMapper.toResponse(menu);
        return new MenuResponse(
                base.menuId(),
                base.menuCode(),
                base.upperMenuId(),
                base.menuName(),
                base.menuCn(),
                base.url(),
                base.srt(),
                base.useYn(),
                base.lvl(),
                buildPath(menu),
                permissionIds
        );
    }

    public String buildOrderKey(Menu menu) {
        LinkedList<String> segments = new LinkedList<>();
        Menu current = menu;
        while (current != null) {
            String srtPart = current.getSrt() != null ? String.format("%05d", current.getSrt()) : "99999";
            segments.addFirst(srtPart + "-" + String.format("%06d", current.getMenuId()));
            current = current.getUpperMenu();
        }
        return String.join("/", segments);
    }

    private String buildPath(Menu menu) {
        LinkedList<String> segments = new LinkedList<>();
        Menu current = menu;
        while (current != null) {
            segments.addFirst(current.getMenuName());
            current = current.getUpperMenu();
        }
        return String.join(" > ", segments);
    }
}
