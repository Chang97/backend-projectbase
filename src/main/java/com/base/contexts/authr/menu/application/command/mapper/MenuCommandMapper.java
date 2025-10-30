package com.base.contexts.authr.menu.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuId;
import com.base.shared.core.util.StringNormalizer;

@Component
public class MenuCommandMapper {

    public Menu toDomain(MenuCommand command) {
        return Menu.create(
                MenuId.of(command.upperMenuId()),
                StringNormalizer.trimToNull(command.menuCode()),
                StringNormalizer.trimToNull(command.menuName()),
                StringNormalizer.trimToNull(command.menuCn()),
                StringNormalizer.trimToNull(command.url()),
                command.srt(),
                command.useYn()
        );
    }

    public void apply(Menu target, MenuCommand command) {
        if (command.menuCode() != null) {
            target.changeCode(StringNormalizer.trimToNull(command.menuCode()));
        }
        if (command.menuName() != null) {
            target.changeName(StringNormalizer.trimToNull(command.menuName()));
        }
        if (command.menuCn() != null) {
            target.changeContent(StringNormalizer.trimToNull(command.menuCn()));
        }
        if (command.url() != null) {
            target.changeUrl(StringNormalizer.trimToNull(command.url()));
        }
        if (command.srt() != null) {
            target.changeSrt(command.srt());
        }
        target.changeUpperMenu(MenuId.of(command.upperMenuId()));
        if (command.useYn() != null) {
            if (Boolean.TRUE.equals(command.useYn())) {
                target.enable();
            } else {
                target.disable();
            }
        }
    }

    public MenuCommandResult toResult(Menu menu) {
        Long id = menu.getMenuId() != null ? menu.getMenuId().value() : null;
        return new MenuCommandResult(id);
    }
}
