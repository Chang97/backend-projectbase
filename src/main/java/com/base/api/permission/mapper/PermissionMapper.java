package com.base.api.permission.mapper;

import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;
import com.base.domain.permission.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionRequest request);

    PermissionResponse toResponse(Permission entity);

    List<PermissionResponse> toResponseList(List<Permission> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(PermissionRequest request, @MappingTarget Permission entity);
}
