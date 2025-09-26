package com.base.application.code.query;

import java.util.List;

import com.base.domain.code.Code;

public interface CodeQueryService {

    List<Code> getCodes();
    Code getCode(Long id);

}
