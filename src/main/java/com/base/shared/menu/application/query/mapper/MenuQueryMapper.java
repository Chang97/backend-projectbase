package com.base.shared.menu.application.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.shared.core.util.StringNormalizer;
import com.base.shared.menu.application.query.dto.MenuQuery;
import com.base.shared.menu.application.query.dto.MenuQueryResult;
import com.base.shared.menu.domain.model.Menu;
import com.base.shared.menu.domain.model.MenuFilter;

@Component
public class MenuQueryMapper {

    public MenuFilter toFilter(MenuQuery query) {
        if (query == null) {
            return MenuFilter.empty();
        }
        return new MenuFilter(
                query.menuId(),
                query.upperMenuId(),
                StringNormalizer.trimToNull(query.menuCode()),
                StringNormalizer.trimToNull(query.menuName()),
                query.useYn()
        );
    }

    public MenuQueryResult toResult(Menu menu, List<Long> permissionIds) {
        return new MenuQueryResult(
                menu.getMenuId() != null ? menu.getMenuId().value() : null,
                menu.getUpperMenuId() != null ? menu.getUpperMenuId().value() : null,
                menu.getMenuCode(),
                menu.getMenuName(),
                menu.getMenuCn(),
                menu.getUrl(),
                menu.getSrt(),
                menu.getUseYn(),
                permissionIds
        );
    }
}
