package com.base.application.permission.query;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 권한 목록 조회 시 사용되는 검색 조건을 보관한다.
 * 프런트에서 전달되는 문자열 기반 파라미터를 후처리(normalize)하고,
 * Boolean 변환이 필요한 항목은 헬퍼 메서드로 제공한다.
 */
@Getter
@Setter
public class PermissionSearchCondition {

    private String permissionName;
    private String useYn;

    /**
     * 공백 제거 등 기본 전처리를 수행한다.
     */
    public void normalize() {
        permissionName = StringUtils.hasText(permissionName) ? permissionName.trim() : null;
        useYn = StringUtils.hasText(useYn) ? useYn.trim() : null;
    }

    /**
     * 화면에서 전달된 Y/N 문자열을 Boolean 값으로 변환한다.
     * 변환 실패 시 null을 반환하여 조건을 건너뛴다.
     */
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
