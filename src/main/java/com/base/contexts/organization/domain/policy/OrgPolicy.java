package com.base.contexts.organization.domain.policy;

import java.util.Objects;
import java.util.function.Function;

import com.base.contexts.organization.domain.model.OrgId;
import com.base.platform.exception.ValidationException;

/**
 * 조직 계층 규칙을 담당하는 정책 객체.
 * 외부 저장소 접근은 함수형 의존성으로 감춘다.
 */
public final class OrgPolicy {

    private final Function<OrgId, Boolean> exists;

    private OrgPolicy(Function<OrgId, Boolean> exists) {
        this.exists = Objects.requireNonNull(exists, "exists");
    }

    public static OrgPolicy using(Function<OrgId, Boolean> exists) {
        return new OrgPolicy(exists);
    }

    public void assertUpperExists(OrgId upperOrgId) {
        if (upperOrgId == null) {
            return;
        }
        boolean present = Boolean.TRUE.equals(exists.apply(upperOrgId));
        if (!present) {
            throw new ValidationException("상위 조직이 존재하지 않습니다. id=" + upperOrgId.value());
        }
    }
}
