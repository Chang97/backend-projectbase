package com.base.infra.redis.token;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.shared.cache.domain.port.out.RefreshTokenStorePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStoreAdapter implements RefreshTokenStorePort {

    private final RefreshTokenStore refreshTokenStore;

    @Override
    public void save(Long userId, String tokenId, String tokenHash, Duration ttl) {
        refreshTokenStore.save(userId, tokenId, tokenHash, ttl);
    }

    @Override
    public Optional<StoredRefreshToken> find(Long userId, String tokenId) {
        return refreshTokenStore.find(userId, tokenId)
                .map(token -> new StoredRefreshToken(token.userId(), token.tokenId(), token.tokenHash()));
    }

    @Override
    public void revoke(Long userId, String tokenId) {
        refreshTokenStore.revoke(userId, tokenId);
    }
}
