package com.base.shared.userrolemap.adapter.out.persistence.repo;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.base.shared.userrolemap.adapter.out.persistence.entity.UserRoleMapEntity;
import com.base.shared.userrolemap.adapter.out.persistence.entity.UserRoleMapEntityId;

public interface UserRoleMapJpaRepository extends JpaRepository<UserRoleMapEntity, UserRoleMapEntityId> {

    @Query("""
            SELECT entity
            FROM UserRoleMapEntity entity
            WHERE entity.userId = :userId
            """)
    List<UserRoleMapEntity> findByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT entity.roleId
            FROM UserRoleMapEntity entity
            WHERE entity.userId = :userId
            """)
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT entity.userId
            FROM UserRoleMapEntity entity
            WHERE entity.roleId IN :roleIds
            """)
    List<Long> findUserIdsByRoleIds(@Param("roleIds") Collection<Long> roleIds);

    @Modifying
    void deleteByUserId(Long userId);

    @Modifying
    void deleteByRoleId(Long roleId);
}
