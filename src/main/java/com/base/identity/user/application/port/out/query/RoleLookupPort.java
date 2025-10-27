package com.base.identity.user.application.port.out.query;

import java.util.Collection;
import java.util.List;

import com.base.domain.role.Role;

public interface RoleLookupPort {

    List<Role> findAllByIds(Collection<Long> roleIds);
}
