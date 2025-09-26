package com.base.application.code.command;

import com.base.domain.code.Code;


public interface CodeCommandService {

    Code createCode(Code code);
    Code updateCode(Long id, Code code);
    void deleteCode(Long id);

    
}
