package com.base.contexts.identity.user.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.domain.model.User;

@Component
public class UserCommandMapper {

    public User toDomain(UserCommand command, String encodedPassword) {
        User user = User.create(
                command.email(),
                command.loginId(),
                encodedPassword,
                command.userName(),
                command.orgId(),
                command.empNo(),
                command.positionName(),
                command.tel(),
                command.statusCodeId()
        );
        if (Boolean.FALSE.equals(command.useYn())) {
            user.disable();
        }
        return user;
    }

    public void apply(User target, UserCommand command) {
        if (command.email() != null) {
            target.changeEmail(command.email());
        }
        if (command.loginId() != null) {
            target.changeLoginId(command.loginId());
        }
        if (command.userName() != null) {
            target.changeUserName(command.userName());
        }
        if (command.tel() != null) {
            target.changeTel(command.tel());
        }
        if (command.positionName() != null) {
            target.changePositionName(command.positionName());
        }
        if (command.empNo() != null) {
            target.changeEmpNo(command.empNo());
        }
        if (command.orgId() != null) {
            target.changeOrg(command.orgId());
        }
        if (command.statusCodeId() != null) {
            target.changeStatus(command.statusCodeId());
        }
        if (command.useYn() != null) {
            if (Boolean.TRUE.equals(command.useYn())) {
                target.enable();
            } else {
                target.disable();
            }
        }
    }
}
