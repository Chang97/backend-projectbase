package com.base.application.menu.query;

import java.util.List;

import com.base.application.menu.usecase.result.MenuResult;
import com.base.application.menu.usecase.result.MenuTreeResult;

public interface UserMenuQueryService {

    /**
     * 사용자 PK 기준으로 접근 가능한 메뉴 트리와 평면 목록을 조회한다.
     */
    UserMenuAccessResult getAccessibleMenus(Long userId);

    record UserMenuAccessResult(
            List<MenuTreeResult> menuTree,
            List<MenuResult> flatMenus
    ) {
    }
}
