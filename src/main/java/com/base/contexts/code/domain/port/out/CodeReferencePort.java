package com.base.contexts.code.domain.port.out;

import java.util.Optional;

import com.base.contexts.code.domain.model.CodeId;
import com.base.contexts.code.domain.model.CodeSnapshot;

public interface CodeReferencePort {

    Optional<CodeSnapshot> load(CodeId codeId);
}
