package com.base.contexts.identity.user.domain.model;

import java.time.LocalDateTime;

import com.base.shared.core.util.StringNormalizer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private final UserId userId;
    private String email;
    private String loginId;
    private String password;
    private String userName;
    private Long orgId;
    private String empNo;
    private String positionName;
    private String tel;
    private Long statusCodeId;
    private LocalDateTime passwordUpdatedAt;
    private Integer passwordFailCount;
    private String previousPassword;
    private Boolean useYn;

    public static User create(String email,
            String loginId,
            String password,
            String userName,
            Long orgId,
            String empNo,
            String positionName,
            String tel,
            Long statusCodeId) {
        return new User(
                null,
                normalize(email),
                normalize(loginId),
                password,
                normalize(userName),
                orgId,
                normalize(empNo),
                normalize(positionName),
                normalize(tel),
                statusCodeId,
                null,
                0,
                null,
                Boolean.TRUE
        );
    }

    public static User restore(UserId userId,
            String email,
            String loginId,
            String password,
            String userName,
            Long orgId,
            String empNo,
            String positionName,
            String tel,
            Long statusCodeId,
            LocalDateTime passwordUpdatedAt,
            Integer passwordFailCount,
            String previousPassword,
            Boolean useYn) {
        return new User(
                userId,
                normalize(email),
                normalize(loginId),
                password,
                normalize(userName),
                orgId,
                normalize(empNo),
                normalize(positionName),
                normalize(tel),
                statusCodeId,
                passwordUpdatedAt,
                passwordFailCount,
                previousPassword,
                defaultUseYn(useYn)
        );
    }

    public void changeUserName(String newUserName) {
        this.userName = normalize(newUserName);
    }

    public void changeTel(String newTel) {
        this.tel = normalize(newTel);
    }

    public void changePositionName(String newPositionName) {
        this.positionName = normalize(newPositionName);
    }

    public void changeEmpNo(String newEmpNo) {
        this.empNo = normalize(newEmpNo);
    }

    public void changeOrg(Long newOrgId) {
        this.orgId = newOrgId;
    }

    public void changeStatus(Long newStatusCodeId) {
        this.statusCodeId = newStatusCodeId;
    }

    public void changeLoginId(String newLoginId) {
        this.loginId = normalize(newLoginId);
    }

    public void changeEmail(String newEmail) {
        this.email = normalize(newEmail);
    }

    public void changePassword(String newPassword, LocalDateTime changedAt) {
        this.previousPassword = this.password;
        this.password = newPassword;
        this.passwordUpdatedAt = changedAt;
        this.passwordFailCount = 0;
    }

    public void registerPasswordFailure() {
        if (this.passwordFailCount == null) {
            this.passwordFailCount = 0;
        }
        this.passwordFailCount += 1;
    }

    public void resetPasswordFailure() {
        this.passwordFailCount = 0;
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

    private static String normalize(String value) {
        return StringNormalizer.trimToNull(value);
    }
}
