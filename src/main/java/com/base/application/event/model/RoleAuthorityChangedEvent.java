package com.base.application.event.model;

import java.util.Collection;

public record RoleAuthorityChangedEvent(
    Collection<Long> userIds
) { }