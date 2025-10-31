package com.base.contexts.code.domain.port.out;

import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.model.CodeId;

public interface CodeHierarchyPort {
    Code getReference(CodeId codeId);
}
