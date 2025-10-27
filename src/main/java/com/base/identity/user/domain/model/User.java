package com.base.identity.user.domain.model;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class User {

    private UserId id;
    private String email;
    private String loginId;
    private String encodedPassword;
    // private Set<UserRole> roles = new LinkedHashSet<>();
    private UserStatus status;
    private OrgId orgId;

    private User(UserId userId,
                 String email,
                 String loginId,
                 String encodedPassword,
                //  Set<UserRole> roles,
                 UserStatus status,
                 OrgId orgId) {
        this.id = userId;
        this.email = email;
        this.loginId = loginId;
        this.encodedPassword = encodedPassword;
        // this.roles = roles != null ? new LinkedHashSet<>(roles) : new LinkedHashSet<>();
        this.status = status;
        this.orgId = orgId;
    }

    public static User create(String email,
                              String loginId,
                              String encodedPassword,
                              UserStatus status,
                              OrgId orgId
                            //   Set<UserRole> roles
                              ) {
        return new User(null, email, loginId, encodedPassword, status, orgId);
    }

    public static User restore(UserId userId,
                               String email,
                               String loginId,
                               String encodedPassword,
                               Set<UserRole> roles,
                               UserStatus status,
                               OrgId orgId) {
        return new User(userId, email, loginId, encodedPassword, status, orgId);
    }

    public void changePassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    // public void syncRoles(Set<UserRole> newRoles) {
    //     roles.clear();
    //     if (newRoles != null) {
    //         roles.addAll(newRoles);
    //     }
    // }

    public void assignOrg(OrgId orgId) {
        this.orgId = orgId;
    }

    public void assignStatus(UserStatus status) {
        this.status = status;
    }

}
