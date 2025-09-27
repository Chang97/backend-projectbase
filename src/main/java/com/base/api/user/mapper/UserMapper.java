package com.base.api.user.mapper;

import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.domain.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "orgId", target = "org.orgId")
    @Mapping(source = "userStatusId", target = "userStatus.codeId")
    User toEntity(UserRequest request);

    @Mapping(source = "org.orgId", target = "orgId")
    @Mapping(source = "org.orgName", target = "orgName")
    @Mapping(source = "userStatus.codeId", target = "userStatusId")
    @Mapping(source = "userStatus.codeName", target = "userStatusName")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(UserRequest request, @MappingTarget User entity);
}
