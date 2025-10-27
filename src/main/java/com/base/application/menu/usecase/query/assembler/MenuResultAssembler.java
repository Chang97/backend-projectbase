package com.base.application.menu.usecase.query.assembler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.menu.usecase.result.MenuResult;
import com.base.domain.menu.Menu;

@Component
public class MenuResultAssembler {

    public MenuResult toResult(Menu menu, List<Long> permissionIds) {
        return new MenuResult(
                menu.getMenuId(),
                menu.getMenuCode(),
                resolveUpperMenuId(menu),
                menu.getMenuName(),
                menu.getMenuCn(),
                menu.getUrl(),
                menu.getSrt(),
                menu.getUseYn(),
                calculateDepth(menu),
                buildPath(menu),
                permissionIds == null ? List.of() : List.copyOf(permissionIds)
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

    private Long resolveUpperMenuId(Menu menu) {
        return menu.getUpperMenu() != null ? menu.getUpperMenu().getMenuId() : null;
    }

    private int calculateDepth(Menu menu) {
        int depth = 0;
        Menu current = menu;
        while (current != null && current.getUpperMenu() != null) {
            depth++;
            current = current.getUpperMenu();
        }
        return depth;
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
