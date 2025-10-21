// MenuRequest.java
package com.base.api.menu.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuRequest(
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<Long> permissionIds
) {
    @JsonCreator
    public MenuRequest(@JsonProperty("menuCode") String menuCode,
                       @JsonProperty("upperMenuId") Long upperMenuId,
                       @JsonProperty("menuName") String menuName,
                       @JsonProperty("menuCn") String menuCn,
                       @JsonProperty("url") String url,
                       @JsonProperty("srt") Integer srt,
                       @JsonProperty("useYn") Boolean useYn,
                       @JsonProperty("permissionIds") List<Long> permissionIds,
                       @JsonProperty("data") Map<String, Object> data) {
        this(
                menuCode,
                upperMenuId != null ? upperMenuId : resolveUpperMenuId(data),
                menuName != null ? menuName : resolveMenuName(data),
                menuCn != null ? menuCn : resolveMenuCn(data),
                url != null ? url : resolveUrl(data),
                srt != null ? srt : resolveSrt(data),
                useYn != null ? useYn : resolveUseYn(data),
                permissionIds != null ? permissionIds : resolvePermissionIds(data)
        );
    }

    private static Long resolveUpperMenuId(Map<String, Object> data) {
        return extractLong(data, "upperMenuId");
    }

    private static String resolveMenuName(Map<String, Object> data) {
        return extractString(data, "menuName");
    }

    private static String resolveMenuCn(Map<String, Object> data) {
        return extractString(data, "menuCn");
    }

    private static String resolveUrl(Map<String, Object> data) {
        return extractString(data, "url");
    }

    private static Integer resolveSrt(Map<String, Object> data) {
        if (data == null || !data.containsKey("srt")) {
            return null;
        }
        Object value = data.get("srt");
        if (value instanceof Number number) {
            return number.intValue();
        }
        String text = Objects.toString(value, "").trim();
        return text.isEmpty() ? null : Integer.valueOf(text);
    }

    private static Boolean resolveUseYn(Map<String, Object> data) {
        if (data == null || !data.containsKey("useYn")) {
            return null;
        }
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

    private static List<Long> resolvePermissionIds(Map<String, Object> data) {
        if (data == null || !data.containsKey("permissionIds")) {
            return null;
        }
        Object value = data.get("permissionIds");
        if (value instanceof List<?> list) {
            return list.stream()
                    .map(MenuRequest::convertToLong)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();
        }
        return null;
    }

    private static Long convertToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        String text = Objects.toString(value, "").trim();
        return text.isEmpty() ? null : Long.valueOf(text);
    }

    private static Long extractLong(Map<String, Object> data, String key) {
        if (data == null || !data.containsKey(key)) {
            return null;
        }
        return convertToLong(data.get(key));
    }

    private static String extractString(Map<String, Object> data, String key) {
        if (data == null || !data.containsKey(key)) {
            return null;
        }
        return Objects.toString(data.get(key), null);
    }
}
