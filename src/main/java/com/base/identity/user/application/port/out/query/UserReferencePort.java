package com.base.identity.user.application.port.out.query;

import com.base.domain.code.Code;
import com.base.domain.org.Org;

/**
 * 사용자 명령 처리 시 필요한 레퍼런스 엔티티를 제공하는 포트.
 */
public interface UserReferencePort {

    Org getOrgReference(Long orgId);

    Code getUserStatusReference(Long codeId);
}
