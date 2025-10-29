package com.base.shared.cache.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AuthorityCachePort {

    Optional<List<String>> get(Long userId);

    void put(Long userId, Collection<String> authorities);

    void evict(Long userId);

    void evictAll(Collection<Long> userIds);
}
