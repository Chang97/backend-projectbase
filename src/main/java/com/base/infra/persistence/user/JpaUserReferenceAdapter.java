package com.base.infra.persistence.user;

import org.springframework.stereotype.Component;

import com.base.application.user.port.out.UserReferencePort;
import com.base.domain.code.Code;
import com.base.domain.org.Org;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUserReferenceAdapter implements UserReferencePort {

    private final EntityManager entityManager;

    @Override
    public Org getOrgReference(Long orgId) {
        return entityManager.getReference(Org.class, orgId);
    }

    @Override
    public Code getUserStatusReference(Long codeId) {
        return entityManager.getReference(Code.class, codeId);
    }
}
