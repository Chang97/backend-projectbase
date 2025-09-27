package com.base.api.code.mapper;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.domain.code.Code;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CodeMapper {

    @Mapping(source = "upperCodeId", target = "upperCode.codeId")
    Code toEntity(CodeRequest request);

    @Mapping(source = "upperCode.codeId", target = "upperCodeId")
    CodeResponse toResponse(Code code);

    List<CodeResponse> toResponseList(List<Code> codes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CodeRequest request, @MappingTarget Code entity);
}
