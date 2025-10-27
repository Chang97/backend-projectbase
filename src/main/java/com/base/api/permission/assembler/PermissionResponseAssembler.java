package com.base.api.permission.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.permission.dto.PermissionResponse;
import com.base.application.permission.result.PermissionResult;

@Component
public class PermissionResponseAssembler {

    public PermissionResponse toResponse(PermissionResult result) {
        return new PermissionResponse(
                result.permissionId(),
                result.permissionCode(),
                result.permissionName(),
                result.useYn()
        );
    }

    public List<PermissionResponse> toResponses(List<PermissionResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
