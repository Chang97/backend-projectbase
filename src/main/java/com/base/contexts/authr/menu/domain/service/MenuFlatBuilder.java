package com.base.contexts.authr.menu.domain.service;

import java.util.*;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.view.FlatMenuView;

public final class MenuFlatBuilder {
  private MenuFlatBuilder() {}

  public static List<FlatMenuView> build(Map<Long, Menu> menus) {
    Map<Long,Integer> depth = computeDepth(menus);

    Comparator<Menu> order = Comparator
        .comparing((Menu m) -> depth.getOrDefault(m.getMenuId().value(), 0))
        .thenComparing(m -> m.getSrt() != null ? m.getSrt() : Integer.MAX_VALUE)
        .thenComparing(m -> m.getMenuId().value());

    return menus.values().stream()
        .sorted(order)
        .map(m -> new FlatMenuView(
            m.getMenuId().value(),
            m.getUpperMenuId() != null ? m.getUpperMenuId().value() : null,
            m.getMenuCode(),
            m.getMenuName(),
            m.getUrl(),
            m.getSrt(),
            Boolean.TRUE.equals(m.getUseYn()),
            depth.getOrDefault(m.getMenuId().value(), 0),
            buildPath(m, menus)
        ))
        .toList();
  }

  static Map<Long,Integer> computeDepth(Map<Long, Menu> menus) {
    Map<Long,Integer> cache = new HashMap<>();
    for (Menu m : menus.values()) depthOf(m, menus, cache);
    return cache;
  }

  private static int depthOf(Menu m, Map<Long,Menu> menus, Map<Long,Integer> cache) {
    Long id = m.getMenuId().value();
    Integer c = cache.get(id);
    if (c != null) return c;
    if (m.getUpperMenuId() == null) {
      cache.put(id, 0);
      return 0; // <- 버그 수정
    }
    Menu p = menus.get(m.getUpperMenuId().value());
    if (p == null) throw new IllegalStateException("Parent missing for menu " + id);
    int d = depthOf(p, menus, cache) + 1;
    cache.put(id, d);
    return d;
  }

  private static String buildPath(Menu m, Map<Long,Menu> menus) {
    LinkedList<String> seg = new LinkedList<>();
    Menu cur = m;
    while (cur != null) {
      seg.addFirst(cur.getMenuName() != null ? cur.getMenuName() : "");
      cur = cur.getUpperMenuId() == null ? null : menus.get(cur.getUpperMenuId().value());
    }
    return String.join(" > ", seg);
  }
}
