package com.base.infra.redis.token;

import java.time.Duration;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.base.infra.redis.property.RefreshTokenProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {

    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public void save(Long userId, String tokenId, String tokenHash, Duration ttl) {
        String key = buildKey(userId, tokenId);
        StoredRefreshToken payload = new StoredRefreshToken(userId, tokenId, tokenHash);
        try {
            String json = objectMapper.writeValueAsString(payload);
            redisTemplate.opsForValue().set(
                    key,
                    json,
                    resolveTtl(ttl)
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Refresh 토큰 직렬화에 실패했습니다.", e);
        } catch (DataAccessException ex) {
            log.error("Redis 저장 실패 key={}", key, ex);
            throw ex;
        }
    }

    @Override
    public Optional<StoredRefreshToken> find(Long userId, String tokenId) {
        String key = buildKey(userId, tokenId);
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(json, StoredRefreshToken.class));
        } catch (JsonProcessingException e) {
            log.warn("Refresh 토큰 역직렬화 실패 key={}", key, e);
            return Optional.empty();
        } catch (DataAccessException ex) {
            log.error("Redis 조회 실패 key={}", key, ex);
            throw ex;
        }
    }

    @Override
    public void revoke(Long userId, String tokenId) {
        String key = buildKey(userId, tokenId);
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException ex) {
            log.error("Redis 토큰 삭제 실패 key={}", key, ex);
            throw ex;
        }
    }

    private Duration resolveTtl(Duration requested) {
        if (requested != null && !requested.isZero() && !requested.isNegative()) {
            return requested;
        }
        return Duration.ofSeconds(properties.ttlSeconds());
    }

    private String buildKey(Long userId, String tokenId) {
        return "%s:%d:%s".formatted(properties.keyPrefix(), userId, tokenId);
    }
}
