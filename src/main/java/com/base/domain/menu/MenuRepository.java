package com.base.domain.menu;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
    Optional<Menu> findByMenuCode(String menuCode);
    Boolean existsByMenuCode(String menuCode);
    List<Menu> findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(Long upperMenuId);
    List<Menu> findByUpperMenu_MenuCodeAndUseYnTrueOrderBySrtAsc(String upperMenuCode);

    /**
     * 사용자 → 역할 → 권한 → 메뉴 매핑을 따라가며 접근 가능한 메뉴만 골라낸다.
     * - useYn=false 는 제외한다.
     * - 중복을 제거하기 위해 DISTINCT 사용.
     */
    @Query("""
            SELECT DISTINCT m
            FROM MenuPermissionMap mpm
            JOIN mpm.menu m
            JOIN mpm.permission p
            JOIN RolePermissionMap rpm ON rpm.permission = p
            JOIN rpm.role r
            JOIN UserRoleMap urm ON urm.role = r
            JOIN urm.user u
            WHERE u.userId = :userId
              AND m.useYn = true
              AND COALESCE(r.useYn, true) = true
              AND COALESCE(p.useYn, true) = true
              AND COALESCE(u.useYn, true) = true
            """)
    List<Menu> findAccessibleMenusByUserId(@Param("userId") Long userId);
}
