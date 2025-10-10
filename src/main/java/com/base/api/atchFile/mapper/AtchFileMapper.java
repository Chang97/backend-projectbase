package com.base.api.atchFile.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.domain.atchFile.AtchFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AtchFileMapper {

    @Mapping(target = "fileGrpCode", ignore = true)
    AtchFile toEntity(AtchFileRequest request);

    @Mapping(source = "fileGrpCode.codeId", target = "fileGrpCodeId")
    @Mapping(source = "fileGrpCode.codeName", target = "fileGrpCodeName")
    AtchFileResponse toResponse(AtchFile entity);

    @Mapping(target = "fileGrpCode", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AtchFileRequest request, @MappingTarget AtchFile entity);
}
