package com.base.application.role.usecase.query.condition;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 역할 목록 조회 시 사용되는 검색 조건.
 */
@Getter
@Setter
public class RoleSearchCondition {

    private String roleName;
    private String useYn;

    public void normalize() {
        roleName = StringUtils.hasText(roleName) ? roleName.trim() : null;
        useYn = StringUtils.hasText(useYn) ? useYn.trim() : null;
    }

    public Boolean getUseYnBoolean() {
        if (!StringUtils.hasText(useYn)) {
            return null;
        }
        return switch (useYn.trim().toUpperCase()) {
            case "Y", "TRUE", "1" -> Boolean.TRUE;
            case "N", "FALSE", "0" -> Boolean.FALSE;
            default -> null;
        };
    }
}
