package com.base.application.permission.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.domain.permission.Permission;

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
    public static Specification<Permission> withCondition(PermissionSearchCondition condition) {
        return (root, query, cb) -> {
            if (condition == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(condition.getPermissionName())) {
                String likeValue = "%" + condition.getPermissionName().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("permissionCode")), likeValue),
                        cb.like(cb.lower(root.get("permissionName")), likeValue)
                ));
            }

            Boolean useYn = condition.getUseYnBoolean();
            if (useYn != null) {
                predicates.add(cb.equal(root.get("useYn"), useYn));
            }

            return predicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
