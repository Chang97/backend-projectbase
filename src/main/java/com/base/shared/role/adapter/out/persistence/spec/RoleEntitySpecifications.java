package com.base.shared.role.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.shared.role.adapter.out.persistence.entity.RoleEntity;
import com.base.shared.role.domain.model.RoleFilter;

import jakarta.persistence.criteria.Predicate;

public final class RoleEntitySpecifications {

    private RoleEntitySpecifications() {
    }

    public static Specification<RoleEntity> withFilter(RoleFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (filter.roleId() != null) {
                    predicates.add(cb.equal(root.get("roleId"), filter.roleId()));
                }
                if (StringUtils.hasText(filter.roleName())) {
                    predicates.add(cb.like(cb.lower(root.get("roleName")), "%" + filter.roleName().toLowerCase() + "%"));
                }
                if (filter.useYn() != null) {
                    predicates.add(cb.equal(root.get("useYn"), filter.useYn()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
