package com.base.application.role.query;

import java.util.List;

import com.base.api.role.dto.RoleResponse;

public interface RoleQueryService {

    RoleResponse getRole(Long id);
    List<RoleResponse> getRoles();

}
