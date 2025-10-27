package com.base.application.code.port.in;

import java.util.List;

import com.base.application.code.usecase.query.condition.CodeSearchCondition;
import com.base.application.code.usecase.result.CodeResult;

public interface GetCodesUseCase {

    List<CodeResult> handle(CodeSearchCondition condition);
}
