package com.base.contexts.authr.menu.application.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.application.query.dto.MenuQuery;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.contexts.authr.menu.application.query.dto.MenuTreeResult;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuFilter;
import com.base.contexts.authr.menu.domain.view.FlatMenuView;
import com.base.contexts.authr.menu.domain.view.MenuTree;
import com.base.shared.core.util.StringNormalizer;

@Component
public class MenuQueryMapper {

    public MenuFilter toFilter(MenuQuery query) {
        if (query == null) return MenuFilter.empty();
        return new MenuFilter(
                query.menuId(),
                query.upperMenuId(),
                StringNormalizer.trimToNull(query.menuCode()),
                StringNormalizer.trimToNull(query.menuName()),
                query.useYn()
        );
    }

    public MenuQueryResult toMenuResult(FlatMenuView v) {
        return new MenuQueryResult(
            v.menuId(),
            v.menuCode(),
            v.upperMenuId(),
            v.menuName(),
            /* menuCn */ null,        // 필요하면 도메인 뷰에 추가
            v.url(),
            v.srt(),
            v.useYn(),
            v.depth(),
            v.path(),
            List.of()                 // 권한 ID는 별도 조합 시 주입
        );
    }

    public MenuQueryResult toMenuResult(FlatMenuView v, List<Long> permissionIds) {
        return new MenuQueryResult(
            v.menuId(),
            v.menuCode(),
            v.upperMenuId(),
            v.menuName(),
            /* menuCn */ null,        // 필요하면 도메인 뷰에 추가
            v.url(),
            v.srt(),
            v.useYn(),
            v.depth(),
            v.path(),
            permissionIds == null ? List.of() : List.copyOf(permissionIds)
        );
    }

    public MenuTreeResult toMenuTreeResult(MenuTree t) {
        List<MenuTreeResult> children = (t.children() == null)
                ? List.of()
                : t.children().stream()
                    .map(this::toMenuTreeResult)
                    .toList();

        return new MenuTreeResult(
                t.menuId(),
                t.menuCode(),
                t.menuName(),
                t.url(),
                children
        );
    }

    public List<MenuTreeResult> toMenuTreeResults(List<MenuTree> trees) {
        if (trees == null || trees.isEmpty()) return List.of();
        return trees.stream().map(this::toMenuTreeResult).toList();
    }

    public MenuQueryResult toQueryResult(Menu menu, List<Long> permissionIds) {
        return new MenuQueryResult(
            menu.getMenuId() != null ? menu.getMenuId().value() : null,
            menu.getMenuCode(),
            menu.getUpperMenuId() != null ? menu.getUpperMenuId().value() : null,
            menu.getMenuName(),
            menu.getMenuCn(),
            menu.getUrl(),
            menu.getSrt(),
            menu.getUseYn(),
            null,
            null,
            permissionIds
        );
    }
}
