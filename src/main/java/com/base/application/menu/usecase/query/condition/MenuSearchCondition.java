package com.base.application.menu.usecase.query.condition;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuSearchCondition {

    private String menuName;
    private String useYn;

    public void normalize() {
        menuName = StringUtils.hasText(menuName) ? menuName.trim() : null;
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
