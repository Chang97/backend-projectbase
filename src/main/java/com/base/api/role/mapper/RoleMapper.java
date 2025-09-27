package com.base.api.role.mapper;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.domain.role.Role;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    // Request → Entity
    Role toEntity(RoleRequest request);

    // Entity → Response
    RoleResponse toResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> menus);

    // Update (Null 무시)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(RoleRequest request, @MappingTarget Role role);
}
