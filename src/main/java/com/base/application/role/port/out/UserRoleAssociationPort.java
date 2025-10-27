package com.base.application.role.port.out;

import java.util.Collection;
import java.util.List;

public interface UserRoleAssociationPort {

    List<Long> findUserIdsByRoleIds(Collection<Long> roleIds);
}
