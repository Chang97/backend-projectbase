package com.base.identity.user.application.command.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.domain.model.OrgId;
import com.base.identity.user.domain.model.User;
import com.base.identity.user.domain.model.UserRole;
import com.base.identity.user.domain.model.UserStatus;

@Component
public class UserCommandMapper {

    public User toDomain(CreateUserCommand command,
                         String encodedPassword,
                         UserStatus status,
                         OrgId orgId
                         ) {
        return User.create(
            command.email(),
            command.loginId(),
            encodedPassword,
            status,
            orgId
        );
    }
//
//  public CreateUserCommand toCreateCommand(UserRequest request) {
    //     return new CreateUserCommand(
    //             request.email(),
    //             request.loginId(),
    //             request.userPassword(),
    //             request.userName(),
    //             request.orgId(),
    //             request.empNo(),
    //             request.pstnName(),
    //             request.tel(),
    //             request.userStatusId(),
    //             request.useYn(),
    //             request.roleIds()
    //     );
    // }

    // public UpdateUserCommand toUpdateCommand(UserRequest request) {
    //     return new UpdateUserCommand(
    //             request.email(),
    //             request.loginId(),
    //             request.userPassword(),
    //             request.userName(),
    //             request.orgId(),
    //             request.empNo(),
    //             request.pstnName(),
    //             request.tel(),
    //             request.userStatusId(),
    //             request.useYn(),
    //             request.roleIds()
    //     );
    // }

    // public ChangePasswordCommand toChangePasswordCommand(Long userId, PasswordChangeRequest request) {
    //     return new ChangePasswordCommand(
    //             userId,
    //             request != null ? request.currentPassword() : null,
    //             request != null ? request.newPassword() : null
    //     );
    // }
}
