package com.base.api.menu.assembler;

import org.springframework.stereotype.Component;

import com.base.api.menu.dto.MenuRequest;
import com.base.application.menu.usecase.command.CreateMenuCommand;
import com.base.application.menu.usecase.command.UpdateMenuCommand;

@Component
public class MenuCommandAssembler {

    public CreateMenuCommand toCreateCommand(MenuRequest request) {
        return new CreateMenuCommand(
                request.menuCode(),
                request.upperMenuId(),
                request.menuName(),
                request.menuCn(),
                request.url(),
                request.srt(),
                request.useYn(),
                request.permissionIds()
        );
    }

    public UpdateMenuCommand toUpdateCommand(MenuRequest request) {
        return new UpdateMenuCommand(
                request.menuCode(),
                request.upperMenuId(),
                request.menuName(),
                request.menuCn(),
                request.url(),
                request.srt(),
                request.useYn(),
                request.permissionIds()
        );
    }
}
