package com.base.application.role.usecase.query.list;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.role.port.in.GetRolesUseCase;
import com.base.application.role.port.out.RolePersistencePort;
import com.base.application.role.port.out.RolePermissionPort;
import com.base.application.role.usecase.query.assembler.RoleResultAssembler;
import com.base.application.role.usecase.query.condition.RoleSearchCondition;
import com.base.application.role.usecase.result.RoleResult;
import com.base.domain.role.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetRolesUseCaseImpl implements GetRolesUseCase {

    private final RolePersistencePort rolePersistencePort;
    private final RolePermissionPort rolePermissionPort;
    private final RoleResultAssembler roleResultAssembler;

    @Override
    public List<RoleResult> handle(RoleSearchCondition condition) {
        RoleSearchCondition criteria = condition != null ? condition : new RoleSearchCondition();
        criteria.normalize();

        List<Role> roles = rolePersistencePort.search(criteria);
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<Long>> permissionMap = rolePermissionPort.findPermissionIdsByRoleIds(
                roles.stream().map(Role::getRoleId).toList()
        );

        return roles.stream()
                .map(role -> roleResultAssembler.toResult(
                        role,
                        permissionMap.getOrDefault(role.getRoleId(), List.of()))
                )
                .toList();
    }
}
