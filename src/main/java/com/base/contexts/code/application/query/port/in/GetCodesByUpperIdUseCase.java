package com.base.contexts.code.application.query.port.in;

import java.util.List;

import com.base.contexts.code.application.query.dto.CodeQueryResult;

public interface GetCodesByUpperIdUseCase {

    List<CodeQueryResult> handle(Long upperCodeId);
}
