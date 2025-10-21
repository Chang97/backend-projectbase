package com.base.api.role.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
    @NotBlank(message = "역할명은 필수입니다.")
    String roleName,
    Boolean useYn,
    List<Long> permissionIds
) {
    @JsonCreator
    public RoleRequest(@JsonProperty("roleName") String roleName,
                       @JsonProperty("useYn") Boolean useYn,
                       @JsonProperty("permissionIds") List<Long> permissionIds,
                       @JsonProperty("data") Map<String, Object> data) {
        this(
            resolveRoleName(roleName, data),
            resolveUseYn(useYn, data),
            resolvePermissionIds(permissionIds, data)
        );
    }

    private static String resolveRoleName(String direct, Map<String, Object> data) {
        if (direct != null) {
            return direct;
        }
        if (data != null && data.containsKey("roleName")) {
            return Objects.toString(data.get("roleName"), null);
        }
        return null;
    }

    private static Boolean resolveUseYn(Boolean direct, Map<String, Object> data) {
        if (direct != null) {
            return direct;
        }
        if (data != null && data.containsKey("useYn")) {
            Object value = data.get("useYn");
            if (value instanceof Boolean bool) {
                return bool;
            }
            String text = Objects.toString(value, "").trim().toUpperCase();
            return switch (text) {
                case "Y", "TRUE", "1" -> Boolean.TRUE;
                case "N", "FALSE", "0" -> Boolean.FALSE;
                default -> null;
            };
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static List<Long> resolvePermissionIds(List<Long> direct, Map<String, Object> data) {
        if (direct != null) {
            return direct;
        }
        if (data != null && data.containsKey("permissionIds")) {
            Object value = data.get("permissionIds");
            if (value instanceof List<?> list) {
                return list.stream()
                        .map(item -> {
                            if (item == null) {
                                return null;
                            }
                            if (item instanceof Number number) {
                                return number.longValue();
                            }
                            String text = Objects.toString(item, "").trim();
                            return text.isEmpty() ? null : Long.valueOf(text);
                        })
                        .filter(Objects::nonNull)
                        .toList();
            }
        }
        return List.of();
    }
}

