package com.base.contexts.code.domain.policy;

import org.springframework.stereotype.Component;

import com.base.contexts.code.domain.model.CodeId;
import com.base.contexts.code.domain.port.out.CodeReferencePort;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

/**
 * 코드 계층 구조 규칙을 담당하는 정책 객체.
 */
@Component
@RequiredArgsConstructor
public class CodePolicy {

    private final CodeReferencePort referencePort;

    /**
     * 상위 코드가 존재한다면 부모 경로를 반환하고, 없으면 ValidationException을 던진다.
     */
    public String resolveParentOrderPath(CodeId upperCodeId) {
        if (upperCodeId == null) {
            return null;
        }
        return referencePort.load(upperCodeId)
                .map(snapshot -> snapshot.orderPath())
                .orElseThrow(() -> new ValidationException("상위 코드가 존재하지 않습니다. id=" + upperCodeId.codeId()));
    }

    public void assertUpperExists(CodeId upperCodeId) {
        resolveParentOrderPath(upperCodeId);
    }
}
