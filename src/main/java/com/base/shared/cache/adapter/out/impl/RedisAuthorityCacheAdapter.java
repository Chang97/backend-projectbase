package com.base.shared.cache.adapter.out.impl;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.base.infra.redis.property.AuthorityCacheProperties;
import com.base.shared.cache.domain.port.out.AuthorityCachePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisAuthorityCacheAdapter implements AuthorityCachePort {

    private static final TypeReference<List<String>> LIST_TYPE = new TypeReference<>() {};

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthorityCacheProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<List<String>> get(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        String key = buildKey(userId);
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(objectMapper.readValue(json, LIST_TYPE));
        } catch (JsonProcessingException ex) {
            log.warn("권한 캐시 역직렬화 실패 key={}", key, ex);
            redisTemplate.delete(key);
            return Optional.empty();
        } catch (DataAccessException ex) {
            log.error("권한 캐시 조회 실패 key={}", key, ex);
            throw ex;
        }
    }

    @Override
    public void put(Long userId, Collection<String> authorities) {
        if (userId == null) {
            return;
        }
        String key = buildKey(userId);
        if (CollectionUtils.isEmpty(authorities)) {
            redisTemplate.delete(key);
            return;
        }
        try {
            String json = objectMapper.writeValueAsString(authorities);
            Duration ttl = Duration.ofSeconds(Math.max(properties.cacheTtlSeconds(), 1L));
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (JsonProcessingException ex) {
            log.error("권한 캐시 직렬화 실패 key={}", key, ex);
        } catch (DataAccessException ex) {
            log.error("권한 캐시 저장 실패 key={}", key, ex);
            throw ex;
        }
    }

    @Override
    public void evict(Long userId) {
        if (userId == null) {
            return;
        }
        String key = buildKey(userId);
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException ex) {
            log.error("권한 캐시 삭제 실패 key={}", key, ex);
            throw ex;
        }
    }

    @Override
    public void evictAll(Collection<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        userIds.stream()
                .filter(id -> id != null)
                .forEach(this::evict);
    }

    private String buildKey(Long userId) {
        return "%s:%d".formatted(properties.keyPrefix(), userId);
    }
}
