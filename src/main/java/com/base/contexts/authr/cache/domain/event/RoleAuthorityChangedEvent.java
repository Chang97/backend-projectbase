package com.base.contexts.authr.cache.domain.event;

import java.util.Collection;

public record RoleAuthorityChangedEvent(
    Collection<Long> userIds
) { }