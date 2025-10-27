package com.base.api.menu.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.dto.MenuTreeResponse;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.application.menu.usecase.result.MenuTreeResult;

@Component
public class MenuResponseAssembler {

    public MenuResponse toResponse(MenuResult result) {
        return new MenuResponse(
                result.menuId(),
                result.menuCode(),
                result.upperMenuId(),
                result.menuName(),
                result.menuCn(),
                result.url(),
                result.srt(),
                result.useYn(),
                result.depth(),
                result.path(),
                result.permissionIds()
        );
    }

    public List<MenuResponse> toResponses(List<MenuResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuTreeResponse toTreeResponse(MenuTreeResult result) {
        if (result == null) {
            return null;
        }
        List<MenuTreeResponse> children = result.children().stream()
                .map(this::toTreeResponse)
                .toList();
        return new MenuTreeResponse(
                result.menuId(),
                result.menuCode(),
                result.upperMenuId(),
                result.menuName(),
                result.menuCn(),
                result.url(),
                result.srt(),
                result.useYn(),
                result.lvl(),
                children
        );
    }

    public List<MenuTreeResponse> toTreeResponses(List<MenuTreeResult> results) {
        return results.stream()
                .map(this::toTreeResponse)
                .toList();
    }
}
