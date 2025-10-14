package com.base.application.menu.query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.dto.MenuTreeResponse;
import com.base.api.menu.mapper.MenuMapper;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserMenuQueryServiceImpl implements UserMenuQueryService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    // 하위 노드 정렬 기준: 메뉴 정렬순서 → 메뉴명 → PK
    private static final Comparator<MenuTreeNode> NODE_ORDER = Comparator
            .comparing((MenuTreeNode node) -> Optional.ofNullable(node.menu.getSrt()).orElse(Integer.MAX_VALUE))
            .thenComparing(node -> Optional.ofNullable(node.menu.getMenuName()).orElse(""))
            .thenComparing(node -> node.menu.getMenuId());

    @Override
    public UserMenuAccessResult getAccessibleMenus(Long userId) {
        // 1) 사용자가 접근 가능한 메뉴(자식만) 목록을 조회한다.
        List<Menu> accessibleMenus = menuRepository.findAccessibleMenusByUserId(userId);
        if (accessibleMenus.isEmpty()) {
            return new UserMenuAccessResult(List.of(), List.of());
        }

        // 2) 상위 메뉴를 반복적으로 수집해 트리를 구성할 준비를 한다.
        Map<Long, Menu> collectedMenus = new LinkedHashMap<>();
        Set<Long> visited = new LinkedHashSet<>();
        accessibleMenus.forEach(menu -> collectWithAncestors(menu, collectedMenus, visited));

        // 3) menu.srt, 깊이, PK 순으로 정렬해 UI에 자연스러운 순서를 보장한다.
        Map<Long, Integer> depthCache = new HashMap<>();
        Comparator<Menu> menuOrder = Comparator
                .<Menu>comparingInt(menu -> resolveDepth(menu, depthCache))
                .thenComparing(menu -> Optional.ofNullable(menu.getSrt()).orElse(Integer.MAX_VALUE))
                .thenComparing(menu -> Optional.ofNullable(menu.getMenuId()).orElse(Long.MAX_VALUE));

        List<Menu> orderedMenus = collectedMenus.values().stream()
                .sorted(menuOrder)
                .toList();

        // 4) 정렬된 메뉴를 이용해 트리 노드를 만든다.
        Map<Long, MenuTreeNode> nodeMap = new LinkedHashMap<>();
        LinkedHashSet<MenuTreeNode> rootNodes = new LinkedHashSet<>();

        for (Menu menu : orderedMenus) {
            MenuTreeNode node = nodeMap.computeIfAbsent(menu.getMenuId(), id -> new MenuTreeNode(menu));
            Menu parent = menu.getUpperMenu();
            if (parent != null && collectedMenus.containsKey(parent.getMenuId())) {
                MenuTreeNode parentNode = nodeMap.computeIfAbsent(parent.getMenuId(), id -> new MenuTreeNode(parent));
                parentNode.children.add(node);
            } else {
                rootNodes.add(node);
            }
        }

        // 5) 평면 리스트와 트리형 구조를 동시에 반환한다.
        List<MenuResponse> flatMenus = orderedMenus.stream()
                .map(menuMapper::toResponse)
                .toList();

        List<MenuTreeResponse> menuTree = rootNodes.stream()
                .map(node -> sortAndConvert(node, 0))
                .toList();

        return new UserMenuAccessResult(menuTree, flatMenus);
    }

    private void collectWithAncestors(Menu menu, Map<Long, Menu> collected, Set<Long> visited) {
        if (menu == null || Boolean.FALSE.equals(menu.getUseYn()) || menu.getMenuId() == null) {
            return;
        }
        if (visited.add(menu.getMenuId())) {
            collected.put(menu.getMenuId(), menu);
            collectWithAncestors(menu.getUpperMenu(), collected, visited);
        }
    }

    private int resolveDepth(Menu menu, Map<Long, Integer> cache) {
        if (menu == null || menu.getMenuId() == null) {
            return 0;
        }
        return cache.computeIfAbsent(menu.getMenuId(), id -> {
            Menu parent = menu.getUpperMenu();
            if (parent == null) {
                return 0;
            }
            return resolveDepth(parent, cache) + 1;
        });
    }

    private MenuTreeResponse sortAndConvert(MenuTreeNode node, int depth) {
        node.children.sort(NODE_ORDER);
        List<MenuTreeResponse> children = node.children.stream()
                .map(child -> sortAndConvert(child, depth + 1))
                .toList();
        Menu menu = node.menu;
        return new MenuTreeResponse(
                menu.getMenuId(),
                menu.getMenuCode(),
                menu.getUpperMenu() != null ? menu.getUpperMenu().getMenuId() : null,
                menu.getMenuName(),
                menu.getMenuCn(),
                menu.getUrl(),
                menu.getSrt(),
                menu.getUseYn(),
                depth,
                children
        );
    }

    private static final class MenuTreeNode {
        private final Menu menu;
        private final List<MenuTreeNode> children = new ArrayList<>();

        private MenuTreeNode(Menu menu) {
            this.menu = menu;
        }
    }
}
