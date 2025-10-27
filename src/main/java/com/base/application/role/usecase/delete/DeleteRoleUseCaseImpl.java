package com.base.application.role.usecase.delete;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.role.port.in.DeleteRoleUseCase;
import com.base.application.role.port.out.RolePersistencePort;
import com.base.application.role.port.out.RolePermissionPort;
import com.base.application.role.port.out.UserRoleAssociationPort;
import com.base.domain.role.Role;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteRoleUseCaseImpl implements DeleteRoleUseCase {

    private final RolePersistencePort rolePersistencePort;
    private final RolePermissionPort rolePermissionPort;
    private final UserRoleAssociationPort userRoleAssociationPort;
    private final CacheInvalidationEventPublisher cachePublisher;

    @Override
    public void handle(Long id) {
        Role existing = rolePersistencePort.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        existing.setUseYn(false);
        rolePersistencePort.save(existing);
        rolePermissionPort.clearPermissions(id);
        List<Long> userIds = userRoleAssociationPort.findUserIdsByRoleIds(List.of(id));
        cachePublisher.publishRoleAuthorityChanged(userIds);
    }

}
