package com.base.shared.menu.adapter.out.persistence.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.base.shared.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.shared.menu.domain.model.MenuFilter;

import jakarta.persistence.criteria.Predicate;

public final class MenuEntitySpecifications {

    private MenuEntitySpecifications() {
    }

    public static Specification<MenuEntity> withFilter(MenuFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (filter.menuId() != null) {
                    predicates.add(cb.equal(root.get("menuId"), filter.menuId()));
                }
                if (filter.upperMenuId() != null) {
                    predicates.add(cb.equal(root.get("upperMenu").get("menuId"), filter.upperMenuId()));
                }
                if (StringUtils.hasText(filter.menuCode())) {
                    predicates.add(cb.equal(root.get("menuCode"), filter.menuCode()));
                }
                if (StringUtils.hasText(filter.menuName())) {
                    predicates.add(cb.like(cb.lower(root.get("menuName")), "%" + filter.menuName().toLowerCase() + "%"));
                }
                if (filter.useYn() != null) {
                    predicates.add(cb.equal(root.get("useYn"), filter.useYn()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
