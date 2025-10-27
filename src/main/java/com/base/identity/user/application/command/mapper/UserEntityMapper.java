package com.base.identity.user.application.command.mapper;

import java.util.LinkedHashSet;

import org.springframework.stereotype.Component;

import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.application.command.dto.UpdateUserCommand;
import com.base.identity.user.domain.model.User;

@Component
public class UserEntityMapper {

    public User toEntity(CreateUserCommand command) {
        User user = new User();
        user.setEmail(command.email());
        user.setLoginId(command.loginId());
        user.setUserName(command.userName());
        user.setEmpNo(command.empNo());
        user.setPstnName(command.pstnName());
        user.setTel(command.tel());
        if (command.useYn() != null) {
            user.setUseYn(command.useYn());
        }
        if (user.getRoles() == null) {
            user.setRoles(new LinkedHashSet<>());
        }
        return user;
    }

    public void applyUpdates(User target, UpdateUserCommand command) {
        if (command.email() != null) {
            target.setEmail(command.email());
        }
        if (command.loginId() != null) {
            target.setLoginId(command.loginId());
        }
        if (command.userName() != null) {
            target.setUserName(command.userName());
        }
        if (command.empNo() != null) {
            target.setEmpNo(command.empNo());
        }
        if (command.pstnName() != null) {
            target.setPstnName(command.pstnName());
        }
        if (command.tel() != null) {
            target.setTel(command.tel());
        }
        if (command.useYn() != null) {
            target.setUseYn(command.useYn());
        }
        if (target.getRoles() == null) {
            target.setRoles(new LinkedHashSet<>());
        }
    }
}
