package com.base.contexts.code.domain.model;

import java.util.Objects;

import lombok.*;

@Getter
public class Code {

    private CodeId codeId;
    private CodeId upperCodeId;
    private String code;
    private String codeName;
    private String description;
    private Integer srt;
    private String orderPath;
    private String etc1;
    private String etc2;
    private String etc3;
    private String etc4;
    private Boolean useYn;

    private Code(CodeId codeId,
            CodeId upperCodeId,
            String code,
            String codeName,
            String description,
            Integer srt,
            String orderPath,
            Boolean useYn,
            String etc1,
            String etc2,
            String etc3,
            String etc4) {

        this.codeId = codeId;
        this.upperCodeId = upperCodeId;
        this.code = Objects.requireNonNull(code);
        this.codeName = codeName;
        this.description = description;
        this.srt = srt;
        this.orderPath = orderPath;
        this.useYn = useYn;
        this.etc1 = etc1;
        this.etc2 = etc2;
        this.etc3 = etc3;
        this.etc4 = etc4;
    }

    public static Code create(String code,
            String codeName,
            String description,
            Integer srt,
            Boolean useYn,
            String etc1,
            String etc2,
            String etc3,
            String etc4,
            CodeId upperCodeId) {

        Code instance = new Code(
            null,
            upperCodeId,
            code,
            codeName,
            description,
            srt,
            null,
            useYn,
            etc1,
            etc2,
            etc3,
            etc4
        );
        instance.refreshOrderPath(null);
        return instance;
    }

    public static Code restore(CodeId codeId,
            CodeId upperCodeId,
            String code,
            String codeName,
            String description,
            Integer srt,
            String orderPath,
            Boolean useYn,
            String etc1,
            String etc2,
            String etc3,
            String etc4) {

        return new Code(
            codeId,
            upperCodeId,
            code,
            codeName,
            description,
            srt,
            orderPath,
            useYn,
            etc1,
            etc2,
            etc3,
            etc4
        );
    }

    public void attachTo(CodeId parentId, String parentOrderPath) {
        upperCodeId = parentId;
        refreshOrderPath(parentOrderPath);
    }

    public void changeCodeName(String newName) {
        this.codeName = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changeSrt(Integer srt) {
        this.srt = srt;
        refreshOrderPath(null); // parentPath는 애플리케이션에서 재호출해 갱신
    }

    public void changeEtc(String etc1, String etc2, String etc3, String etc4) {
        this.etc1 = etc1;
        this.etc2 = etc2;
        this.etc3 = etc3;
        this.etc4 = etc4;
    }

    public void enable() {
        this.useYn = true;
    }

    public void disable() {
        this.useYn = false;
    }


    private void refreshOrderPath(String parentOrderPath) {
        String segment = buildOrderSegment(srt, code);
        if (parentOrderPath == null || parentOrderPath.isBlank()) {
            this.orderPath = segment;
            return;
        }
        this.orderPath = parentOrderPath + ">" + segment;
    }

    private String buildOrderSegment(Integer srt, String codeValue) {
        int order = (srt != null) ? srt : 999_999;
        String paddedOrder = String.format("%06d", Math.min(order, 999_999));
        return paddedOrder + ":" + (codeValue == null ? "" : codeValue);
    }
}
