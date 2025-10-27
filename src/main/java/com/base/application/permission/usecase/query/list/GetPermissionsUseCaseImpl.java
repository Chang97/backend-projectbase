package com.base.application.permission.usecase.query.list;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.auth.port.PermissionCachePort;
import com.base.application.permission.result.PermissionResult;
import com.base.application.permission.usecase.query.assembler.PermissionResultAssembler;
import com.base.application.permission.usecase.query.condition.PermissionSearchCondition;
import com.base.application.permission.usecase.query.condition.PermissionSpecifications;
import com.base.domain.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPermissionsUseCaseImpl implements GetPermissionsUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionCachePort permissionCachePort;
    private final PermissionResultAssembler permissionResultAssembler;

    @Override
    public List<PermissionResult> handle(PermissionSearchCondition condition) {
        PermissionSearchCondition criteria = condition != null ? condition : new PermissionSearchCondition();
        criteria.normalize();

        Boolean useYnFilter = criteria.getUseYnBoolean();
        String nameFilter = criteria.getPermissionName();

        return permissionCachePort.get(nameFilter, useYnFilter)
                .orElseGet(() -> {
                    List<PermissionResult> results = permissionRepository.findAll(
                                    PermissionSpecifications.withCondition(criteria),
                                    Sort.by(Sort.Order.asc("permissionCode"))
                            )
                            .stream()
                            .map(permissionResultAssembler::toResult)
                            .toList();
                    permissionCachePort.put(nameFilter, useYnFilter, results);
                    return results;
                });
    }
}
