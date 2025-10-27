package com.base.application.permission.usecase.query.assembler;

import org.springframework.stereotype.Component;

import com.base.application.permission.result.PermissionResult;
import com.base.domain.permission.Permission;

@Component
public class PermissionResultAssembler {

    public PermissionResult toResult(Permission permission) {
        return new PermissionResult(
                permission.getPermissionId(),
                permission.getPermissionCode(),
                permission.getPermissionName(),
                permission.getUseYn()
        );
    }
}
