package com.base.identity.user.application.command.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.base.application.user.usecase.result.UserResult;
import com.base.domain.mapping.UserRoleMap;
import com.base.domain.role.Role;
import com.base.domain.user.User;

@Component
public class UserResultMapper {

    public UserResult toResult(User user) {
        if (user == null) {
            return null;
        }

        Long orgId = user.getOrg() != null ? user.getOrg().getOrgId() : null;
        String orgName = user.getOrg() != null ? user.getOrg().getOrgName() : null;
        Long userStatusId = user.getUserStatus() != null ? user.getUserStatus().getCodeId() : null;
        String userStatusName = user.getUserStatus() != null ? user.getUserStatus().getCodeName() : null;

        Map<Long, String> roleInfo = new HashMap<>();
        if (user.getRoles() != null) {
            for (UserRoleMap mapping : user.getRoles()) {
                if (mapping == null) {
                    continue;
                }
                Role role = mapping.getRole();
                if (role == null || role.getRoleId() == null) {
                    continue;
                }
                roleInfo.putIfAbsent(role.getRoleId(), role.getRoleName());
            }
        }

        List<Map.Entry<Long, String>> sortedRoles = new ArrayList<>(roleInfo.entrySet());
        sortedRoles.sort(Map.Entry.comparingByValue(Comparator.nullsLast(String::compareToIgnoreCase)));

        List<Long> roleIds = sortedRoles.stream()
                .map(Map.Entry::getKey)
                .toList();
        List<String> roleNames = sortedRoles.stream()
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .toList();
        String roleNameList = roleNames.isEmpty() ? "" : String.join(", ", roleNames);

        return new UserResult(
                user.getUserId(),
                user.getEmail(),
                user.getLoginId(),
                user.getUserName(),
                orgId,
                orgName,
                user.getEmpNo(),
                user.getPstnName(),
                user.getTel(),
                userStatusId,
                userStatusName,
                user.getUseYn(),
                roleIds,
                roleNames,
                roleNameList
        );
    }
}
