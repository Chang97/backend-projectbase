package com.base.application.role.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.domain.role.Role;

import jakarta.persistence.criteria.Predicate;

public final class RoleSpecifications {

    private RoleSpecifications() {
    }

    public static Specification<Role> withCondition(RoleSearchCondition condition) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (condition != null) {
                if (StringUtils.hasText(condition.getRoleName())) {
                    String likeValue = "%" + condition.getRoleName().toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(root.get("roleName")), likeValue));
                }
                Boolean useYn = condition.getUseYnBoolean();
                if (useYn != null) {
                    predicates.add(cb.equal(root.get("useYn"), useYn));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
