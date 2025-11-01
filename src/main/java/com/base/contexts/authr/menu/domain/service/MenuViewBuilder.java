package com.base.contexts.authr.menu.domain.service;

import java.util.*;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.view.FlatMenuView;
import com.base.contexts.authr.menu.domain.view.MenuViews;
import com.base.contexts.authr.menu.domain.view.MenuTree;

public final class MenuViewBuilder {
    private MenuViewBuilder() {}

    public static MenuViews build(Map<Long, Menu> menus) {
        // 1) 트리
        List<MenuTree> tree = MenuTreeBuilder.build(menus);

        // 2) 깊이 계산 (메모이제이션, Map만 사용)
        Map<Long, Integer> depth = computeDepthMap(menus);

        // 3) 평면 정렬 + 프로젝션(도메인 뷰)
        Comparator<Menu> order = Comparator
            .comparing((Menu m) -> depth.getOrDefault(m.getMenuId().value(), 0))
            .thenComparing(m -> nvl(m.getSrt(), Integer.MAX_VALUE))
            .thenComparing(m -> m.getMenuId().value());

        List<FlatMenuView> flat = menus.values().stream()
            .sorted(order)
            .map(m -> new FlatMenuView(
                m.getMenuId().value(),
                m.getUpperMenuId() != null ? m.getUpperMenuId().value() : null,
                m.getMenuCode(),
                m.getMenuName(),
                m.getMenuCn(),
                m.getUrl(),
                m.getSrt(),
                Boolean.TRUE.equals(m.getUseYn()),
                depth.getOrDefault(m.getMenuId().value(), 0),
                buildPath(m, menus)
            ))
            .toList();

        return new MenuViews(List.copyOf(tree), List.copyOf(flat));
    }

    // ------- helpers (no repository calls) -------

    static Map<Long, Integer> computeDepthMap(Map<Long, Menu> menus) {
        Map<Long, Integer> cache = new HashMap<>();
        for (Menu m : menus.values()) {
        depthOf(m, menus, cache, new HashSet<>());
        }
        return cache;
    }

    private static int depthOf(Menu m, Map<Long, Menu> menus,
                                Map<Long, Integer> cache, Set<Long> stack) {
        Long id = m.getMenuId().value();
        Integer c = cache.get(id);
        if (c != null) return c;

        if (!stack.add(id)) {
        throw new IllegalStateException("Cycle detected in menu hierarchy at " + id);
        }

        if (m.getUpperMenuId() == null) {
        cache.put(id, 0);
        stack.remove(id);
        return 0;
        }

        Menu parent = menus.get(m.getUpperMenuId().value());
        if (parent == null) {
        throw new IllegalStateException("Parent missing for menu " + id);
        }

        int d = depthOf(parent, menus, cache, stack) + 1;
        cache.put(id, d);
        stack.remove(id);
        return d;
    }

    private static String buildPath(Menu m, Map<Long, Menu> menus) {
        LinkedList<String> seg = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        Menu cur = m;
        while (cur != null) {
        Long id = cur.getMenuId().value();
        if (!visited.add(id)) {
            throw new IllegalStateException("Cycle detected while building path at " + id);
        }
        seg.addFirst(nvl(cur.getMenuName(), ""));
        cur = (cur.getUpperMenuId() == null) ? null : menus.get(cur.getUpperMenuId().value());
        if (cur == null && m.getUpperMenuId() != null) {
            // 상위 ID는 있는데 Map에 없음 → 입력 전제 위반
            // (원하면 여기서 빈 문자열 추가 대신 예외)
        }
        }
        return String.join(" > ", seg);
    }

    private static <T> T nvl(T v, T d) { return v != null ? v : d; }
    }
