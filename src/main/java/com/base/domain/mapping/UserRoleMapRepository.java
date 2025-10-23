package com.base.domain.mapping;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.base.domain.user.User;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, UserRoleMapId> {

    List<UserRoleMap> findAllByUser(User user);

    void deleteByUser(User user);

    @Query("""
            SELECT DISTINCT r.roleName
            FROM UserRoleMap urm
            JOIN urm.role r
            WHERE urm.user.userId = :userId
              AND COALESCE(r.useYn, true) = true
            """)
    List<String> findRoleNamesByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT p.permissionCode
            FROM UserRoleMap urm
            JOIN urm.role r
            JOIN RolePermissionMap rpm ON rpm.role = r
            JOIN rpm.permission p
            WHERE urm.user.userId = :userId
              AND COALESCE(r.useYn, true) = true
              AND COALESCE(p.useYn, true) = true
            """)
    List<String> findPermissionCodesByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT urm.user.userId
            FROM UserRoleMap urm
            WHERE urm.role.roleId IN :roleIds
            """)
    List<Long> findUserIdsByRoleIds(@Param("roleIds") Collection<Long> roleIds);
}
