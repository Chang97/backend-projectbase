package com.base.shared.permission.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.shared.permission.adapter.out.persistence.entity.PermissionEntity;
import com.base.shared.permission.domain.model.PermissionFilter;

import jakarta.persistence.criteria.Predicate;

public final class PermissionEntitySpecifications {

    private PermissionEntitySpecifications() {
    }

    public static Specification<PermissionEntity> withFilter(PermissionFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (filter.permissionId() != null) {
                    predicates.add(cb.equal(root.get("permissionId"), filter.permissionId()));
                }
                if (StringUtils.hasText(filter.permissionCode())) {
                    predicates.add(cb.equal(cb.lower(root.get("permissionCode")), filter.permissionCode().toLowerCase()));
                }
                if (StringUtils.hasText(filter.permissionName())) {
                    predicates.add(cb.like(cb.lower(root.get("permissionName")), "%" + filter.permissionName().toLowerCase() + "%"));
                }
                if (filter.useYn() != null) {
                    predicates.add(cb.equal(root.get("useYn"), filter.useYn()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
