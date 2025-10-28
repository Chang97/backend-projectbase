package com.base.shared.menu.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Menu {

    private final MenuId menuId;
    private MenuId upperMenuId;
    private String menuCode;
    private String menuName;
    private String menuCn;
    private String url;
    private Integer srt;
    private Boolean useYn;

    public static Menu create(MenuId upperMenuId,
            String menuCode,
            String menuName,
            String menuCn,
            String url,
            Integer srt,
            Boolean useYn) {
        return new Menu(
                null,
                upperMenuId,
                menuCode,
                menuName,
                menuCn,
                url,
                srt,
                defaultUseYn(useYn)
        );
    }

    public static Menu restore(MenuId menuId,
            MenuId upperMenuId,
            String menuCode,
            String menuName,
            String menuCn,
            String url,
            Integer srt,
            Boolean useYn) {
        return new Menu(
                menuId,
                upperMenuId,
                menuCode,
                menuName,
                menuCn,
                url,
                srt,
                defaultUseYn(useYn)
        );
    }

    public void changeCode(String newCode) {
        this.menuCode = newCode;
    }

    public void changeName(String newName) {
        this.menuName = newName;
    }

    public void changeContent(String newContent) {
        this.menuCn = newContent;
    }

    public void changeUrl(String newUrl) {
        this.url = newUrl;
    }

    public void changeSrt(Integer newSrt) {
        this.srt = newSrt;
    }

    public void changeUpperMenu(MenuId newUpperMenuId) {
        this.upperMenuId = newUpperMenuId;
    }

    public void enable() {
        this.useYn = true;
    }

    public void disable() {
        this.useYn = false;
    }

    private static Boolean defaultUseYn(Boolean useYn) {
        return useYn != null ? useYn : Boolean.TRUE;
    }
}
