package com.base.api.user.mapper;

import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.domain.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "org", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(source = "org.orgId", target = "orgId")
    @Mapping(source = "org.orgName", target = "orgName")
    @Mapping(source = "userStatus.codeId", target = "userStatusId")
    @Mapping(source = "userStatus.codeName", target = "userStatusName")
    UserResponse toResponse(User user);

    @Mapping(target = "org", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(UserRequest request, @MappingTarget User entity);
}

