package com.base.contexts.authr.permission.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.contexts.authr.permission.adapter.out.persistence.entity.PermissionEntity;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;

import jakarta.persistence.criteria.Predicate;

/**
 * {@link PermissionSearchCondition}을 기반으로 동적 조회 조건을 조합하는 Specification 유틸리티.
 */
public final class PermissionSpecifications {

    private PermissionSpecifications() {
    }

    /**
     * 검색 조건을 기반으로 Permission 엔티티용 Predicate를 생성한다.
     */
    public static Specification<PermissionEntity> withCondition(PermissionFilter condition) {
        return (root, query, cb) -> {
            if (condition == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(condition.permissionCode())) {
                predicates.add(cb.equal(root.get("permissionCode"), condition.permissionCode()));
            }

            if (StringUtils.hasText(condition.permissionName())) {
                String likeValue = "%" + condition.permissionName().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("permissionCode")), likeValue),
                        cb.like(cb.lower(root.get("permissionName")), likeValue)
                ));
            }

            Boolean useYn = condition.useYn();
            if (useYn != null) {
                predicates.add(cb.equal(root.get("useYn"), useYn));
            }

            return predicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
