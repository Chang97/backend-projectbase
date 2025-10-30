package com.base.contexts.authr.role.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.role.application.command.dto.RoleCommand;
import com.base.contexts.authr.role.application.command.dto.RoleCommandResult;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.shared.core.util.StringNormalizer;

@Component
public class RoleCommandMapper {

    public Role toDomain(RoleCommand command) {
        return Role.create(
                StringNormalizer.trimToNull(command.roleName()),
                command.useYn()
        );
    }

    public void apply(Role target, RoleCommand command) {
        if (command.roleName() != null) {
            target.changeName(StringNormalizer.trimToNull(command.roleName()));
        }
        if (command.useYn() != null) {
            if (Boolean.TRUE.equals(command.useYn())) {
                target.enable();
            } else {
                target.disable();
            }
        }
    }

    public RoleCommandResult toResult(Role role) {
        Long id = role.getRoleId() != null ? role.getRoleId().value() : null;
        return new RoleCommandResult(id);
    }
}
