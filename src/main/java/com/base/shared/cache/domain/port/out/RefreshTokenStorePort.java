package com.base.shared.cache.domain.port.out;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenStorePort {
    void save(Long userId, String tokenId, String tokenHash, Duration ttl);

    Optional<StoredRefreshToken> find(Long userId, String tokenId);

    void revoke(Long userId, String tokenId);

    record StoredRefreshToken(Long userId, String tokenId, String tokenHash) {}
}
