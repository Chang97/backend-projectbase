package com.base.application.code.port.out;

import java.util.List;
import java.util.Optional;

import com.base.application.code.usecase.query.condition.CodeSearchCondition;
import com.base.domain.code.Code;

public interface CodePersistencePort {

    Code save(Code code);

    Optional<Code> findById(Long codeId);

    Optional<Code> findByCode(String code);

    boolean existsByCode(String code);

    List<Code> findChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperCode(String upperCode);

    List<Code> search(CodeSearchCondition condition);
}
