package com.base.shared.cache.domain.event;

import java.util.Collection;

public record RoleAuthorityChangedEvent(
    Collection<Long> userIds
) { }