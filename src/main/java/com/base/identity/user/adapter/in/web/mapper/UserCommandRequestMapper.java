package com.base.identity.user.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.identity.user.adapter.in.web.dto.PasswordChangeRequest;
import com.base.identity.user.adapter.in.web.dto.UserRequest;
import com.base.identity.user.application.command.dto.ChangePasswordCommand;
import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.application.command.dto.UpdateUserCommand;


@Component
public class UserCommandRequestMapper {

    public CreateUserCommand toCreateCommand(UserRequest request) {
        return new CreateUserCommand(
                request.email(),
                request.loginId(),
                request.userPassword(),
                request.userName(),
                request.orgId(),
                request.empNo(),
                request.pstnName(),
                request.tel(),
                request.userStatusId(),
                request.useYn(),
                request.roleIds()
        );
    }

    public UpdateUserCommand toUpdateCommand(UserRequest request) {
        return new UpdateUserCommand(
                request.email(),
                request.loginId(),
                request.userPassword(),
                request.userName(),
                request.orgId(),
                request.empNo(),
                request.pstnName(),
                request.tel(),
                request.userStatusId(),
                request.useYn(),
                request.roleIds()
        );
    }

    public ChangePasswordCommand toChangePasswordCommand(Long userId, PasswordChangeRequest request) {
        return new ChangePasswordCommand(
                userId,
                request != null ? request.currentPassword() : null,
                request != null ? request.newPassword() : null
        );
    }
}
