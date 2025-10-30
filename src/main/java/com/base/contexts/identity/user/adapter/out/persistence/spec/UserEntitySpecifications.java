package com.base.contexts.identity.user.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.base.contexts.identity.user.adapter.out.persistence.entity.UserEntity;
import com.base.contexts.identity.user.domain.model.UserFilter;

import jakarta.persistence.criteria.Predicate;

public final class UserEntitySpecifications {

    private UserEntitySpecifications() {
    }

    public static Specification<UserEntity> withFilter(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (filter.userId() != null) {
                    predicates.add(cb.equal(root.get("userId"), filter.userId()));
                }
                Set<Long> userIds = filter.userIds();
                if (!CollectionUtils.isEmpty(userIds)) {
                    predicates.add(root.get("userId").in(userIds));
                }
                if (StringUtils.hasText(filter.email())) {
                    predicates.add(cb.equal(cb.lower(root.get("email")), filter.email().toLowerCase()));
                }
                if (StringUtils.hasText(filter.loginId())) {
                    predicates.add(cb.equal(cb.lower(root.get("loginId")), filter.loginId().toLowerCase()));
                }
                if (StringUtils.hasText(filter.userName())) {
                    predicates.add(cb.like(cb.lower(root.get("userName")), "%" + filter.userName().toLowerCase() + "%"));
                }
                if (filter.orgId() != null) {
                    predicates.add(cb.equal(root.get("orgId"), filter.orgId()));
                }
                if (filter.statusCodeId() != null) {
                    predicates.add(cb.equal(root.get("userStatusCodeId"), filter.statusCodeId()));
                }
                if (filter.useYn() != null) {
                    predicates.add(cb.equal(root.get("useYn"), filter.useYn()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
