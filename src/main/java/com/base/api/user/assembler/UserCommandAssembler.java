package com.base.api.user.assembler;

import org.springframework.stereotype.Component;

import com.base.api.user.dto.PasswordChangeRequest;
import com.base.api.user.dto.UserRequest;
import com.base.application.user.usecase.command.ChangePasswordCommand;
import com.base.application.user.usecase.command.CreateUserCommand;
import com.base.application.user.usecase.command.UpdateUserCommand;

@Component
public class UserCommandAssembler {

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
