package com.base.infra.persistence.code;

import org.springframework.stereotype.Component;

import com.base.application.code.port.out.CodeReferencePort;
import com.base.domain.code.Code;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaCodeReferenceAdapter implements CodeReferencePort {

    private final EntityManager entityManager;

    @Override
    public Code getReference(Long codeId) {
        return entityManager.getReference(Code.class, codeId);
    }
}
