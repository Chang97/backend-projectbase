package com.base.shared.menu.adapter.in.web.command.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.base.shared.core.util.StringNormalizer;
import com.base.shared.menu.adapter.in.web.command.dto.MenuCommandRequest;
import com.base.shared.menu.adapter.in.web.command.dto.MenuCommandResponse;
import com.base.shared.menu.application.command.dto.MenuCommand;
import com.base.shared.menu.application.command.dto.MenuCommandResult;

@Component
public class MenuCommandWebMapper {

    public MenuCommand toCommand(MenuCommandRequest request) {
        return new MenuCommand(
                request.upperMenuId(),
                StringNormalizer.trimToNull(request.menuCode()),
                StringNormalizer.trimToNull(request.menuName()),
                StringNormalizer.trimToNull(request.menuCn()),
                StringNormalizer.trimToNull(request.url()),
                request.srt(),
                request.useYn(),
                sanitizePermissionIds(request.permissionIds())
        );
    }

    public MenuCommandResponse toResponse(MenuCommandResult result) {
        return new MenuCommandResponse(result.menuId());
    }

    private List<Long> sanitizePermissionIds(List<Long> permissionIds) {
        if (permissionIds == null) {
            return List.of();
        }
        return permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }
}
