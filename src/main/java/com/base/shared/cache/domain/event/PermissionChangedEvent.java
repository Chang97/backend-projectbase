package com.base.shared.cache.domain.event;

import java.util.Collection;

public record PermissionChangedEvent(
    Collection<Long> affectedUserIds
) { }