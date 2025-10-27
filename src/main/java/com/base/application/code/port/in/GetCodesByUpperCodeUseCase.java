package com.base.application.code.port.in;

import java.util.List;

import com.base.application.code.usecase.result.CodeResult;

public interface GetCodesByUpperCodeUseCase {

    List<CodeResult> handle(String upperCode);
}
