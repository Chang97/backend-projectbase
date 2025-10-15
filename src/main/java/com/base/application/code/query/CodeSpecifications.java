package com.base.application.code.query;

import com.base.domain.code.Code;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link CodeSearchCondition}를 기반으로 동적 조회 조건을 조합하는 Specification 헬퍼.
 */
public final class CodeSpecifications {

    private CodeSpecifications() {
    }

    /**
     * 검색 조건을 기반으로 JPA Criteria Predicate를 생성한다.
     */
    public static Specification<Code> withCondition(CodeSearchCondition condition) {
        return (root, query, cb) -> {
            if (Code.class.equals(query.getResultType())) {
                root.fetch("upperCode", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();
            if (condition != null) {
                if (StringUtils.hasText(condition.getUpperCode())) {
                    predicates.add(cb.equal(
                        cb.lower(root.get("upperCode").get("code")),
                        condition.getUpperCode().toLowerCase()
                    ));
                }
                if (StringUtils.hasText(condition.getCodeName())) {
                    String likeValue = "%" + condition.getCodeName().toLowerCase() + "%";
                    predicates.add(
                         cb.or(
                            cb.like(cb.lower(root.get("codeName")), likeValue),
                            cb.like(cb.lower(root.get("code")), likeValue)
                        )
                    );
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
