package com.base.shared.code.application.query.port.in;

import java.util.List;

import com.base.shared.code.application.query.dto.CodeQueryResult;

public interface GetCodesByUpperCodeUseCase {

    List<CodeQueryResult> handle(String upperCode);
}
