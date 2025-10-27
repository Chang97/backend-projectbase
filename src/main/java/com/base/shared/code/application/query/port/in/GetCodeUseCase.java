package com.base.shared.code.application.query.port.in;

import com.base.shared.code.application.query.dto.CodeQueryResult;

public interface GetCodeUseCase {

    CodeQueryResult handle(Long codeId);
}
