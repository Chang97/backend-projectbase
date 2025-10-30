package com.base.contexts.authr.cache.domain.port.out;

import java.util.Collection;

public interface AuthorityCacheEventPort {

    void publishRoleAuthoritiesChanged(Collection<Long> userIds);

    void publishPermissionsChanged(Collection<Long> userIds);
}
