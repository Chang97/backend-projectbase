package com.base.application.event.publisher;

import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.base.application.event.model.PermissionChangedEvent;
import com.base.application.event.model.RoleAuthorityChangedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheInvalidationEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishRoleAuthorityChanged(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        publisher.publishEvent(new RoleAuthorityChangedEvent(userIds));
    }

    public void publishPermissionChanged(Collection<Long> userIds) {
        Collection<Long> safeUserIds = CollectionUtils.isEmpty(userIds) ? List.of() : List.copyOf(userIds);
        publisher.publishEvent(new PermissionChangedEvent(safeUserIds));
    }
}
