package com.base.application.code.usecase.query.condition;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeSearchCondition {
    private String upperCode;
    private String codeName;
    private String useYn;

    public void normalize() {
        upperCode = StringUtils.hasText(upperCode) ? upperCode.trim() : null;
        codeName = StringUtils.hasText(codeName) ? codeName.trim() : null;
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
