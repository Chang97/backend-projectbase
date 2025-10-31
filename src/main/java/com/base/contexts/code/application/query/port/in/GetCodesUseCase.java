package com.base.contexts.code.application.query.port.in;

import java.util.List;

import com.base.contexts.code.application.query.dto.CodeQuery;
import com.base.contexts.code.application.query.dto.CodeQueryResult;

public interface GetCodesUseCase {

    List<CodeQueryResult> handle(CodeQuery condition);
}
