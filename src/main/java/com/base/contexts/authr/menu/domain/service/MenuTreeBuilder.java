package com.base.contexts.authr.menu.domain.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.view.MenuTree;

public final class MenuTreeBuilder {
    private MenuTreeBuilder() {}

    private static final Comparator<Menu> ORDER = Comparator
        .comparing((Menu m) -> m.getSrt() != null ? m.getSrt() : Integer.MAX_VALUE)
        .thenComparing(m -> m.getMenuName() != null ? m.getMenuName() : "")
        .thenComparing(m -> m.getMenuId().value());

    public static List<MenuTree> build(Map<Long, Menu> menus) {
        Map<Long, List<Menu>> children = new LinkedHashMap<>();
        for (Menu m : menus.values()) {
        Long parentId = m.getUpperMenuId() != null ? m.getUpperMenuId().value() : null;
        children.computeIfAbsent(parentId, k -> new ArrayList<>()).add(m);
        }
        List<Menu> roots = children.getOrDefault(null, List.of()).stream().sorted(ORDER).toList();
        List<MenuTree> tree = new ArrayList<>(roots.size());
        for (Menu root : roots) {
        tree.add(toTree(root, children));
        }
        return List.copyOf(tree);
    }

    private static MenuTree toTree(Menu menu, Map<Long, List<Menu>> children) {
        var kidMenus = children.getOrDefault(menu.getMenuId().value(), List.of()).stream()
            .sorted(ORDER).toList();
        var kidTrees = new ArrayList<MenuTree>(kidMenus.size());
        for (Menu m : kidMenus) {
        kidTrees.add(toTree(m, children));
        }
        return new MenuTree(
            menu.getMenuId().value(),
            menu.getMenuCode(),
            menu.getMenuName(),
            menu.getUrl(),
            List.copyOf(kidTrees)
        );
    }
}

