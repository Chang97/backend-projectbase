package com.base.application.user.port.out;

import java.util.Collection;
import java.util.List;

import com.base.domain.role.Role;

public interface RoleLookupPort {

    List<Role> findAllByIds(Collection<Long> roleIds);
}
