package com.base.application.role.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.api.role.mapper.RoleMapper;
import com.base.api.role.mapper.RoleMapperImpl;
import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.role.query.RoleResponseAssembler;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.domain.role.RoleRepository;

import static org.mockito.Mockito.mock;

@Disabled("Requires JPA test schema configuration for embedded database; enable when shared test profile is available.")
@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.hbm2ddl.auto=create-drop",
        "spring.datasource.common.jdbc-url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.common.driver-class-name=org.h2.Driver",
        "spring.datasource.common.username=sa",
        "spring.datasource.common.password=",
        "spring.datasource.common.maximum-pool-size=5",
        "spring.datasource.common.minimum-idle=1",
        "spring.flyway.enabled=false",
        "spring.jpa.show-sql=false",
        "decorator.datasource.p6spy.enabled=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RoleCommandServiceImplTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionMapRepository rolePermissionMapRepository;

    @Autowired
    private RoleRepository roleRepository;

    private RoleCommandService roleCommandService;

    @BeforeEach
    void setUp() {
        RoleMapper roleMapper = new RoleMapperImpl();
        RoleResponseAssembler roleResponseAssembler = new RoleResponseAssembler(roleMapper);
        UserRoleMapRepository userRoleMapRepository = mock(UserRoleMapRepository.class);
        CacheInvalidationEventPublisher cacheInvalidationEventPublisher = mock(CacheInvalidationEventPublisher.class);
        roleCommandService = new RoleCommandServiceImpl(
                roleRepository,
                roleMapper,
                rolePermissionMapRepository,
                permissionRepository,
                roleResponseAssembler,
                userRoleMapRepository,
                cacheInvalidationEventPublisher
        );
    }

    @Test
    void createRolePersistsPermissionMappings() {
        Permission permission = permissionRepository.save(
                Permission.builder()
                        .permissionCode("PERM-" + UUID.randomUUID())
                        .permissionName("테스트 권한")
                        .build());

        RoleResponse response = roleCommandService.createRole(
                new RoleRequest("ADMIN-" + UUID.randomUUID(), true, List.of(permission.getPermissionId())));

        assertThat(rolePermissionMapRepository.findPermissionIdsByRoleId(response.roleId()))
                .containsExactly(permission.getPermissionId());
        assertThat(response.permissionIds()).containsExactly(permission.getPermissionId());
    }

    @Test
    void updateRoleSyncsPermissionMappings() {
        Permission permissionA = permissionRepository.save(
                Permission.builder()
                        .permissionCode("PERM-" + UUID.randomUUID())
                        .permissionName("권한 A")
                        .build());
        Permission permissionB = permissionRepository.save(
                Permission.builder()
                        .permissionCode("PERM-" + UUID.randomUUID())
                        .permissionName("권한 B")
                        .build());

        String roleName = "MANAGER-" + UUID.randomUUID();

        RoleResponse created = roleCommandService.createRole(
                new RoleRequest(roleName, true, List.of(permissionA.getPermissionId())));

        RoleResponse updated = roleCommandService.updateRole(
                created.roleId(),
                new RoleRequest(roleName, true,
                        List.of(permissionA.getPermissionId(), permissionB.getPermissionId())));

        assertThat(rolePermissionMapRepository.findPermissionIdsByRoleId(updated.roleId()))
                .containsExactlyInAnyOrder(permissionA.getPermissionId(), permissionB.getPermissionId());
        assertThat(updated.permissionIds())
                .containsExactlyInAnyOrder(permissionA.getPermissionId(), permissionB.getPermissionId());

        RoleResponse unchanged = roleCommandService.updateRole(
                updated.roleId(),
                new RoleRequest(roleName, true, null));

        assertThat(rolePermissionMapRepository.findPermissionIdsByRoleId(unchanged.roleId()))
                .containsExactlyInAnyOrder(permissionA.getPermissionId(), permissionB.getPermissionId());
        assertThat(unchanged.permissionIds())
                .containsExactlyInAnyOrder(permissionA.getPermissionId(), permissionB.getPermissionId());

        RoleResponse cleared = roleCommandService.updateRole(
                unchanged.roleId(),
                new RoleRequest(roleName, true, List.of()));

        assertThat(rolePermissionMapRepository.findPermissionIdsByRoleId(cleared.roleId())).isEmpty();
        assertThat(cleared.permissionIds()).isEmpty();
    }
}
