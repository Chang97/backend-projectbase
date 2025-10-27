package com.base.shared.code.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.shared.code.adapter.out.persistence.entity.CodeEntity;
import com.base.shared.code.domain.model.CodeFilter;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public final class CodeEntitySpecifications {

    private CodeEntitySpecifications() {
    }

    public static Specification<CodeEntity> withFilter(CodeFilter filter) {
        return (root, query, cb) -> {
            if (CodeEntity.class.equals(query.getResultType())) {
                root.fetch("upperCode", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (StringUtils.hasText(filter.upperCode())) {
                    predicates.add(cb.equal(
                            cb.lower(root.get("upperCode").get("code")),
                            filter.upperCode().toLowerCase()
                    ));
                }
                if (filter.upperCodeId() != null) {
                    predicates.add(cb.equal(root.get("upperCode").get("codeId"), filter.upperCodeId()));
                }
                if (StringUtils.hasText(filter.code())) {
                    predicates.add(cb.equal(cb.lower(root.get("code")), filter.code().toLowerCase()));
                }
                if (StringUtils.hasText(filter.codeName())) {
                    String likeValue = "%" + filter.codeName().toLowerCase() + "%";
                    predicates.add(
                            cb.or(
                                    cb.like(cb.lower(root.get("codeName")), likeValue),
                                    cb.like(cb.lower(root.get("code")), likeValue)
                            )
                    );
                }
                if (filter.useYn() != null) {
                    predicates.add(cb.equal(root.get("useYn"), filter.useYn()));
                }
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
