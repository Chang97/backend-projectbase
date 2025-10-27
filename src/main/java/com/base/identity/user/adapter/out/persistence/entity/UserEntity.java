package com.base.identity.user.adapter.out.persistence.entity;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import com.base.domain.code.Code;
import com.base.domain.common.BaseEntity;
import com.base.domain.mapping.UserRoleMap;
import com.base.domain.org.Org;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users") // 예약어 회피용 복수형 테이블명
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Org org;

    @Column(name = "emp_no", length = 100)
    private String empNo;

    @Column(name = "pstn_name", length = 200)
    private String pstnName;

    @Column(length = 100)
    private String tel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_code_id")
    private Code userStatus;

    private OffsetDateTime userPasswordUpdateDt;
    private Integer userPasswordFailCnt;

    @Column(name = "old1_user_password", length = 400)
    private String old1UserPassword;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleMap> roles = new HashSet<>();

}
