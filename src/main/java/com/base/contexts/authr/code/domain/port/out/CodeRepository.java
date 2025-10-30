package com.base.contexts.authr.code.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.code.domain.model.Code;
import com.base.contexts.authr.code.domain.model.CodeFilter;


public interface CodeRepository {
    Code save(Code code);

    Optional<Code> findById(Long codeId);

    Optional<Code> findByCode(String code);

    boolean existsByCode(String code);

    List<Code> findChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperCode(String upperCode);

    List<Code> search(CodeFilter filter);
}
