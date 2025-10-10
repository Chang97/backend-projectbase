package com.base.api.permission.mapper;

import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;
import com.base.domain.permission.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    Permission toEntity(PermissionRequest request);

    PermissionResponse toResponse(Permission entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(PermissionRequest request, @MappingTarget Permission entity);
}
