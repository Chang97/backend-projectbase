package com.base.api.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.domain.user.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "org", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    User toEntity(UserRequest request);

    default UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        Long orgId = user.getOrg() != null ? user.getOrg().getOrgId() : null;
        String orgName = user.getOrg() != null ? user.getOrg().getOrgName() : null;
        Long userStatusId = user.getUserStatus() != null ? user.getUserStatus().getCodeId() : null;
        String userStatusName = user.getUserStatus() != null ? user.getUserStatus().getCodeName() : null;

        Map<Long, String> roleInfo = new HashMap<>();
        if (user.getRoles() != null) {
            user.getRoles().stream()
                    .filter(Objects::nonNull)
                    .filter(map -> map.getRole() != null)
                    .forEach(map -> roleInfo.putIfAbsent(
                            map.getRole().getRoleId(),
                            map.getRole().getRoleName()
                    ));
        }

        List<Map.Entry<Long, String>> sortedRoles = new ArrayList<>(roleInfo.entrySet());
        sortedRoles.sort(Map.Entry.comparingByValue(Comparator.nullsLast(String::compareToIgnoreCase)));

        List<Long> roleIds = sortedRoles.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<String> roleNames = sortedRoles.stream()
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        String roleNameList = roleNames.isEmpty() ? "" : String.join(", ", roleNames);

        return new UserResponse(
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

    @Mapping(target = "org", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(UserRequest request, @MappingTarget User entity);
}
