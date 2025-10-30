package com.base.contexts.authr.code.application.query.port.in;

import com.base.contexts.authr.code.application.query.dto.CodeQueryResult;

public interface GetCodeUseCase {

    CodeQueryResult handle(Long codeId);
}
