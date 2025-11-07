package com.base.contexts.authr.menu.domain.policy;

import java.util.Objects;
import java.util.function.Function;

import com.base.contexts.authr.menu.domain.model.MenuId;
import com.base.platform.exception.ValidationException;

/**
 * 메뉴 계층 구조와 관련된 도메인 규칙을 캡슐화한다.
 * 외부 인프라(저장소) 의존성은 Function 주입으로 감춘다.
 */
public final class MenuPolicy {

    private final Function<MenuId, Boolean> exists;

    private MenuPolicy(Function<MenuId, Boolean> exists) {
        this.exists = Objects.requireNonNull(exists, "exists");
    }

    public static MenuPolicy using(Function<MenuId, Boolean> exists) {
        return new MenuPolicy(exists);
    }

    /**
     * 상위 메뉴가 존재해야 한다는 규칙을 보장한다.
     */
    public void assertUpperExists(MenuId upperMenuId) {
        if (upperMenuId == null) {
            return;
        }
        boolean present = Boolean.TRUE.equals(exists.apply(upperMenuId));
        if (!present) {
            throw new ValidationException("상위 메뉴가 존재하지 않습니다. id=" + upperMenuId.value());
        }
    }
}
