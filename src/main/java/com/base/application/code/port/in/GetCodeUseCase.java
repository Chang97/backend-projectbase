package com.base.application.code.port.in;

import com.base.application.code.usecase.result.CodeResult;

public interface GetCodeUseCase {

    CodeResult handle(Long codeId);
}
