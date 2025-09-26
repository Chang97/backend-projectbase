package com.base.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users") // 예약어 회피용 복수형 테이블명
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; // 사용자 PK

    @Column(nullable = false, unique = true, length = 200)
    private String email; // 이메일 (로그인 계정)

    @Column(name = "login_id", length = 100)
    private String loginId; // 로그인 ID

    @Column(name = "user_password", length = 400, nullable = false)
    private String userPassword; // 암호화된 비밀번호

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName; // 사용자 이름

    @Column(name = "org_id")
    private Integer orgId; // 소속 조직 ID (org 테이블 참조)

    @Column(name = "emp_no", length = 100)
    private String empNo; // 사번

    @Column(name = "pstn_name", length = 200)
    private String pstnName; // 직위명

    @Column(length = 100)
    private String tel; // 전화번호

    @Column(name = "user_status_code_id")
    private Integer userStatusCodeId; // 사용자 상태 코드 (code 테이블 참조)

    @Column(name = "user_password_update_dt")
    private OffsetDateTime userPasswordUpdateDt; // 비밀번호 변경일시

    @Column(name = "user_password_fail_cnt")
    private Integer userPasswordFailCnt; // 비밀번호 실패 횟수

    @Column(name = "old1_user_password", length = 400)
    private String old1UserPassword; // 이전 비밀번호

    @Builder.Default
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn = true; // 사용 여부

    @Column(name = "created_id")
    private Integer createdId; // 생성자 ID

    @Column(name = "created_dt", nullable = false,
            columnDefinition = "timestamptz default now()")
    private OffsetDateTime createdDt;

    @Column(name = "updated_id")
    private Integer updatedId; // 수정자 ID

    @Column(name = "updated_dt", nullable = false,
            columnDefinition = "timestamptz default now()")
    private OffsetDateTime updatedDt;

}
