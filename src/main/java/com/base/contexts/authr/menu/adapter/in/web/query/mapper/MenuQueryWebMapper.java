package com.base.contexts.authr.menu.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.adapter.in.web.query.dto.MenuQueryRequest;
import com.base.contexts.authr.menu.adapter.in.web.query.dto.MenuQueryResponse;
import com.base.contexts.authr.menu.application.query.dto.MenuQuery;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;
import com.base.shared.core.util.StringNormalizer;

@Component
public class MenuQueryWebMapper {

    public MenuQuery toQuery(MenuQueryRequest request) {
        if (request == null) {
            return new MenuQuery(null, null, null, null, null);
        }
        return new MenuQuery(
                request.menuId(),
                request.upperMenuId(),
                StringNormalizer.trimToNull(request.menuCode()),
                StringNormalizer.trimToNull(request.menuName()),
                request.useYn()
        );
    }

    public MenuQueryResponse toResponse(MenuQueryResult result) {
        return new MenuQueryResponse(
                result.menuId(),
                result.upperMenuId(),
                result.menuCode(),
                result.menuName(),
                result.menuCn(),
                result.url(),
                result.srt(),
                result.useYn(),
                result.permissionIds()
        );
    }

    public List<MenuQueryResponse> toResponses(List<MenuQueryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
