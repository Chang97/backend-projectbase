package com.base.contexts.code.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.code.domain.model.Code;

public interface CodeCommandPort {

    Code save(Code code);

    Optional<Code> findById(Long codeId);

    Optional<Code> findByCode(String code);

    boolean existsByCode(String code);

    List<Code> findChildrenByUpperId(Long upperCodeId);
}
