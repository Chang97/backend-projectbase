package com.base.shared.code.domain.port.out;

import com.base.shared.code.domain.model.Code;
import com.base.shared.code.domain.model.CodeId;

public interface CodeHierarchyPort {
    Code getReference(CodeId codeId);
}
