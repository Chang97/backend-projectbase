package com.base.application.event.model;

import java.util.Collection;

public record PermissionChangedEvent(
    Collection<Long> affectedUserIds
) { }