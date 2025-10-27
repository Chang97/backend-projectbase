package com.base.infra.persistence.role;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.user.port.out.RoleLookupPort;
import com.base.domain.role.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaRoleLookupAdapter implements RoleLookupPort {

    private final JpaRoleRepository roleRepository;

    @Override
    public List<Role> findAllByIds(Collection<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }
}
