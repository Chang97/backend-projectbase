package com.base.application.code.port.out;

import com.base.domain.code.Code;

public interface CodeReferencePort {

    Code getReference(Long codeId);
}
