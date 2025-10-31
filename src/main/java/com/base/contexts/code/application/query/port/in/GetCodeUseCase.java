package com.base.contexts.code.application.query.port.in;

import com.base.contexts.code.application.query.dto.CodeQueryResult;

public interface GetCodeUseCase {

    CodeQueryResult handle(Long codeId);
}
