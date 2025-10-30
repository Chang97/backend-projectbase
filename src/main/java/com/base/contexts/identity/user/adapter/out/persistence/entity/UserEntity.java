package com.base.contexts.identity.user.adapter.out.persistence.entity;

import java.time.OffsetDateTime;

import com.base.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(length = 100)
    private String loginId;

    @Column(name = "user_password", length = 400)
    private String userPassword;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "emp_no", length = 100)
    private String empNo;

    @Column(name = "pstn_name", length = 200)
    private String pstnName;

    @Column(length = 100)
    private String tel;

    @Column(name = "user_status_code_id")
    private Long userStatusCodeId;

    @Column(name = "user_password_update_dt")
    private OffsetDateTime userPasswordUpdateDt;

    @Column(name = "user_password_fail_cnt")
    private Integer userPasswordFailCnt;

    @Column(name = "old1_user_password", length = 400)
    private String old1UserPassword;
}
