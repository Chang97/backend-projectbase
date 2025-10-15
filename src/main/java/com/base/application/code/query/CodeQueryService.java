package com.base.application.code.query;

import java.util.List;

import com.base.api.code.dto.CodeResponse;

public interface CodeQueryService {

    CodeResponse getCode(Long id);
    List<CodeResponse> getCodes(CodeSearchCondition condition);
    List<CodeResponse> getCodesByUpperId(Long upperCodeId);
    List<CodeResponse> getCodesByUpperCode(String upperCode);

}
