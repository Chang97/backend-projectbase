package com.base.contexts.authr.cache.adapter.out.impl;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.platform.redis.property.PermissionCacheProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionCacheAdapter {

    private static final TypeReference<List<PermissionQueryResult>> LIST_TYPE = new TypeReference<>() {};

    private final RedisTemplate<String, String> redisTemplate;
    private final PermissionCacheProperties properties;
    private final ObjectMapper objectMapper;

    public Optional<List<PermissionQueryResult>> get(String permissionName, Boolean useYn) {
        String key = buildKey(permissionName, useYn);
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(objectMapper.readValue(json, LIST_TYPE));
        } catch (JsonProcessingException ex) {
            log.warn("Permission 캐시 역직렬화 실패 key={}", key, ex);
            redisTemplate.delete(key);
            return Optional.empty();
        } catch (DataAccessException ex) {
            log.error("Permission 캐시 조회 실패 key={}", key, ex);
            throw ex;
        }
    }

    public void put(String permissionName, Boolean useYn, List<PermissionQueryResult> responses) {
        String key = buildKey(permissionName, useYn);
        if (responses == null || responses.isEmpty()) {
            redisTemplate.delete(key);
            return;
        }
        try {
            String json = objectMapper.writeValueAsString(responses);
            Duration ttl = Duration.ofSeconds(Math.max(properties.ttlSeconds(), 1L));
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (JsonProcessingException ex) {
            log.error("Permission 캐시 직렬화 실패 key={}", key, ex);
        } catch (DataAccessException ex) {
            log.error("Permission 캐시 저장 실패 key={}", key, ex);
            throw ex;
        }
    }

    public void evict(String permissionName, Boolean useYn) {
        String key = buildKey(permissionName, useYn);
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException ex) {
            log.error("Permission 캐시 삭제 실패 key={}", key, ex);
            throw ex;
        }
    }

    public void evictAll() {
        String pattern = "%s:*".formatted(properties.keyPrefix());
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String buildKey(String permissionName, Boolean useYn) {
        String normalizedName = StringUtils.hasText(permissionName) ? permissionName.trim().toLowerCase() : "";
        String normalizedUseYn = useYn == null ? "*" : Boolean.TRUE.equals(useYn) ? "Y" : "N";
        return "%s:%s:%s".formatted(properties.keyPrefix(), normalizedUseYn, normalizedName);
    }
}
