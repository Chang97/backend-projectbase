package com.base.authr.application.handler;

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

import com.base.authr.application.dto.MenuTreeResult;
import com.base.authr.application.port.in.GetResolveMenusUseCase;
import com.base.exception.NotFoundException;
import com.base.shared.menu.domain.model.Menu;
import com.base.shared.menu.domain.port.out.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetResolveMenusHandler implements GetResolveMenusUseCase {

    private final MenuRepository menuRepository;

    // 하위 노드 정렬 기준: 메뉴 정렬순서 → 메뉴명 → PK
    private static final Comparator<MenuTreeNode> NODE_ORDER = Comparator
        .comparing((MenuTreeNode node) -> Optional.ofNullable(node.menu.getSrt()).orElse(Integer.MAX_VALUE))
        .thenComparing(node -> Optional.ofNullable(node.menu.getMenuName()).orElse(""))
        .thenComparing(node -> node.menu.getMenuId().value());

    @Override
    public List<MenuTreeResult> handle(Long userId) {
        List<Menu> accessibleMenus = menuRepository.findAccessibleMenusByUserId(userId);
        if (accessibleMenus.isEmpty()) {
            return List.of(new MenuTreeResult(null, null, null, null, List.of()));
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
                .thenComparing(menu -> Optional.ofNullable(menu.getMenuId().value()).orElse(Long.MAX_VALUE));

        List<Menu> orderedMenus = collectedMenus.values().stream()
                .sorted(menuOrder)
                .toList();

        // 4) 정렬된 메뉴를 이용해 트리 노드를 만든다.
        Map<Long, MenuTreeNode> nodeMap = new LinkedHashMap<>();
        LinkedHashSet<MenuTreeNode> rootNodes = new LinkedHashSet<>();

        for (Menu menu : orderedMenus) {
            MenuTreeNode node = nodeMap.computeIfAbsent(menu.getMenuId().value(), id -> new MenuTreeNode(menu));
            Menu parent = menuRepository.findById(menu.getUpperMenuId().value())
                    .orElseThrow(() -> new NotFoundException("Upper Menu Not Found") );
            if (parent != null && collectedMenus.containsKey(parent.getMenuId().value())) {
                MenuTreeNode parentNode = nodeMap.computeIfAbsent(parent.getMenuId().value(), id -> new MenuTreeNode(parent));
                parentNode.children.add(node);
            } else {
                rootNodes.add(node);
            }
        }

        // 5) 평면 리스트와 트리형 구조를 동시에 반환한다.
        // List<MenuResult> flatMenus = orderedMenus.stream()
        //         .map(menu -> menuResultAssembler.toResult(menu, List.of()))
        //         .toList();

        List<MenuTreeResult> menuTree = rootNodes.stream()
                .map(node -> sortAndConvert(node, 0))
                .toList();

        return menuTree;
        
    }

    private void collectWithAncestors(Menu menu, Map<Long, Menu> collected, Set<Long> visited) {
        if (menu == null || Boolean.FALSE.equals(menu.getUseYn()) || menu.getMenuId() == null) {
            return;
        }
        if (visited.add(menu.getMenuId().value())) {
            collected.put(menu.getMenuId().value(), menu);
            Menu parent = menuRepository.findById(menu.getUpperMenuId().value())
                    .orElseThrow(() -> new NotFoundException("Upper Menu Not Found") );
            collectWithAncestors(parent, collected, visited);
        }
    }

    private int resolveDepth(Menu menu, Map<Long, Integer> cache) {
        if (menu == null || menu.getMenuId() == null) {
            return 0;
        }
        Long id = menu.getMenuId().value();
        Integer cached = cache.get(id);
        if (cached != null) {
            return cached;
        }
        Menu parent = menuRepository.findById(menu.getUpperMenuId().value())
                    .orElseThrow(() -> new NotFoundException("Upper Menu Not Found") );
        int depth = parent == null ? 0 : resolveDepth(parent, cache) + 1;
        cache.put(id, depth);
        return depth;
    }

    private MenuTreeResult sortAndConvert(MenuTreeNode node, int depth) {
        node.children.sort(NODE_ORDER);
        List<MenuTreeResult> children = node.children.stream()
                .map(child -> sortAndConvert(child, depth + 1))
                .toList();
        Menu menu = node.menu;
        return new MenuTreeResult(
                menu.getMenuId().value(),
                menu.getMenuCode(),
                menu.getMenuName(),
                menu.getUrl(),
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
