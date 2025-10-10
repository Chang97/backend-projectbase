package com.base.api.code.mapper;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.domain.code.Code;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CodeMapper {

    @Mapping(target = "upperCode", ignore = true)
    Code toEntity(CodeRequest request);

    @Mapping(source = "upperCode.codeId", target = "upperCodeId")
    CodeResponse toResponse(Code code);

    @Mapping(target = "upperCode", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CodeRequest request, @MappingTarget Code entity);
}

