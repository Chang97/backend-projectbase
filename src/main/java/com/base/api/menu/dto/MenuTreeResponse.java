package com.base.api.menu.dto;

import java.util.List;

/**
 * 사용자별 메뉴 구성을 트리 형태로 전달하기 위한 DTO.
 */
public record MenuTreeResponse(
        Long menuId,
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<MenuTreeResponse> children
) {
}
