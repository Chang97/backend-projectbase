package com.base.application.user.usecase.support;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.base.application.user.port.out.RoleLookupPort;
import com.base.application.user.port.out.UserReferencePort;
import com.base.domain.mapping.UserRoleMap;
import com.base.domain.role.Role;
import com.base.domain.user.User;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCommandSupport {

    private final UserReferencePort userReferencePort;
    private final RoleLookupPort roleLookupPort;
    private final PasswordEncoder passwordEncoder;

    public void applyReferences(User user, Long orgId, Long userStatusId) {
        if (orgId != null) {
            user.setOrg(userReferencePort.getOrgReference(orgId));
        } else {
            user.setOrg(null);
        }
        if (userStatusId != null) {
            user.setUserStatus(userReferencePort.getUserStatusReference(userStatusId));
        } else {
            user.setUserStatus(null);
        }
    }

    public void applyPassword(User user, String rawPassword) {
        if (StringUtils.hasText(rawPassword)) {
            user.setUserPassword(passwordEncoder.encode(rawPassword));
        }
    }

    public void syncUserRoles(User user, List<Long> roleIds) {
        if (user.getRoles() == null) {
            user.setRoles(new LinkedHashSet<>());
        }
        Set<UserRoleMap> currentMappings = user.getRoles();

        if (CollectionUtils.isEmpty(roleIds)) {
            currentMappings.clear();
            return;
        }

        Set<Long> distinctRoleIds = roleIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        currentMappings.removeIf(mapping -> {
            Role role = mapping.getRole();
            Long roleId = role != null ? role.getRoleId() : null;
            return roleId == null || !distinctRoleIds.contains(roleId);
        });

        Set<Long> existingRoleIds = currentMappings.stream()
                .map(UserRoleMap::getRole)
                .filter(Objects::nonNull)
                .map(Role::getRoleId)
                .collect(Collectors.toSet());

        Set<Long> missingRoleIds = distinctRoleIds.stream()
                .filter(roleId -> !existingRoleIds.contains(roleId))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (missingRoleIds.isEmpty()) {
            return;
        }

        List<Role> rolesToAdd = roleLookupPort.findAllByIds(missingRoleIds);
        if (rolesToAdd.size() != missingRoleIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        Map<Long, Role> roleById = rolesToAdd.stream()
                .filter(role -> role.getRoleId() != null)
                .collect(Collectors.toMap(
                        Role::getRoleId,
                        role -> role,
                        (existing, duplicate) -> existing,
                        LinkedHashMap::new
                ));

        missingRoleIds.forEach(roleId -> {
            Role role = roleById.get(roleId);
            if (role == null) {
                throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
            }
            currentMappings.add(UserRoleMap.builder()
                    .user(user)
                    .role(role)
                    .build());
        });
    }
}
