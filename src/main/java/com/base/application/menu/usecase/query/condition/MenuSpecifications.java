package com.base.application.menu.usecase.query.condition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.domain.menu.Menu;

import jakarta.persistence.criteria.Predicate;

public final class MenuSpecifications {

    private MenuSpecifications() {
    }

    public static Specification<Menu> withCondition(MenuSearchCondition condition) {
        return (root, query, cb) -> {
            if (Menu.class.equals(query.getResultType())) {
                query.distinct(true);
            }
            List<Predicate> predicates = new ArrayList<>();
            if (condition != null) {
                if (StringUtils.hasText(condition.getMenuName())) {
                    String likeValue = "%" + condition.getMenuName().toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(root.get("menuName")), likeValue));
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
