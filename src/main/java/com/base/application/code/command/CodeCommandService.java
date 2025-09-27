package com.base.application.code.command;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;

public interface CodeCommandService {

    CodeResponse createCode(CodeRequest request);
    CodeResponse updateCode(Long id, CodeRequest request);
    void deleteCode(Long id);

    
}
