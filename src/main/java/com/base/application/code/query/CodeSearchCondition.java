package com.base.application.code.query;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 코드 목록 조회 시 사용되는 검색 조건을 보관한다.
 * 프런트에서 넘어오는 값은 문자열 기반이므로, 후처리(normalize)로 공백 제거 및 Boolean 변환을 담당한다.
 */
@Getter
@Setter
public class CodeSearchCondition {
    private String upperCode;
    private String codeName;
    private String useYn;

    /**
     * 공백 제거 등 1차 전처리를 수행한다.
     */
    public void normalize() {
        upperCode = StringUtils.hasText(upperCode) ? upperCode.trim() : null;
        codeName = StringUtils.hasText(codeName) ? codeName.trim() : null;
        useYn = StringUtils.hasText(useYn) ? useYn.trim() : null;
    }

    /**
     * 화면에서 전달된 Y/N 문자열을 JPA 쿼리에서 사용할 수 있도록 Boolean으로 변환한다.
     * 변환이 어려운 값은 null을 반환해 조건을 건너뛴다.
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
