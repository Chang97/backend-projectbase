package com.base.contexts.organization.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Org {

    private OrgId orgId;
    private OrgId upperOrgId;
    private String orgCode;
    private String orgName;
    private Integer srt;
    private Boolean useYn;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Org(OrgId orgId,
            OrgId upperOrgId,
            String orgCode,
            String orgName,
            Integer srt,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.orgId = orgId;
        this.upperOrgId = upperOrgId;
        this.orgCode = Objects.requireNonNull(orgCode, "orgCode must not be null");
        this.orgName = orgName;
        this.srt = srt;
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Org create(String orgCode,
            String orgName,
            Integer srt,
            OrgId upperOrgId,
            Boolean useYn) {
        return new Org(null, upperOrgId, orgCode, orgName, srt, useYn, null, null, null, null);
    }

    public static Org restore(OrgId orgId,
            OrgId upperOrgId,
            String orgCode,
            String orgName,
            Integer srt,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Org(orgId, upperOrgId, orgCode, orgName, srt, useYn, createdBy, updatedBy, createdAt, updatedAt);
    }

    public OrgId getOrgId() {
        return orgId;
    }

    public OrgId getUpperOrgId() {
        return upperOrgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public Integer getSrt() {
        return srt;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changeOrgCode(String orgCode) {
        this.orgCode = Objects.requireNonNull(orgCode, "orgCode must not be null");
    }

    public void changeOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void changeSrt(Integer srt) {
        this.srt = srt;
    }

    public void changeUseYn(Boolean useYn) {
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
    }

    public void attachTo(OrgId parentId) {
        this.upperOrgId = parentId;
    }

    public void markPersisted(OrgId orgId,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.orgId = orgId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateAudit(Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
