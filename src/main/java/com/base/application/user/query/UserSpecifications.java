package com.base.application.user.query;

import com.base.domain.mapping.UserRoleMap;
import com.base.domain.org.Org;
import com.base.domain.role.Role;
import com.base.domain.user.User;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link UserSearchCondition}를 기반으로 동적 조회 조건을 조합하는 Specification 헬퍼.
 */
public final class UserSpecifications {

    private UserSpecifications() {
    }

    /**
     * 검색 조건을 기반으로 JPA Criteria Predicate를 생성한다.
     */
    public static Specification<User> withCondition(UserSearchCondition condition) {
        return (root, query, cb) -> {
            if (User.class.equals(query.getResultType())) {
                root.fetch("org", JoinType.LEFT);
                root.fetch("roles", JoinType.LEFT).fetch("role", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();
            if (condition != null) {
                if (StringUtils.hasText(condition.getOrgName())) {
                Join<User, Org> orgJoin = root.join("org", JoinType.LEFT);
                    predicates.add(cb.equal(
                        cb.lower(orgJoin.get("orgName")),
                        condition.getOrgName().toLowerCase()
                    ));
                }
                if (StringUtils.hasText(condition.getLoginId())) {
                    String likeValue = "%" + condition.getLoginId().toLowerCase() + "%";
                    predicates.add(
                        cb.or(
                            cb.like(cb.lower(root.get("loginId")), likeValue),
                            cb.like(cb.lower(root.get("userName")), likeValue)
                        )
                    );
                }
                if (StringUtils.hasText(condition.getRoleName())) {
                    Join<User, UserRoleMap> userRoleJoin = root.join("roles", JoinType.LEFT);
                    Join<UserRoleMap, Role> roleJoin = userRoleJoin.join("role", JoinType.LEFT);
                    String likeValue = "%" + condition.getRoleName().toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(roleJoin.get("roleName")), likeValue));
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
