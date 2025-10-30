package com.base.contexts.authr.code.domain.port.out;

import com.base.contexts.authr.code.domain.model.Code;
import com.base.contexts.authr.code.domain.model.CodeId;

public interface CodeHierarchyPort {
    Code getReference(CodeId codeId);
}
