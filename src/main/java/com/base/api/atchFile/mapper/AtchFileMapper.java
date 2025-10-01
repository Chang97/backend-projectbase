package com.base.api.atchFile.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.domain.atchFile.AtchFile;

@Mapper(componentModel = "spring")
public interface AtchFileMapper {

    @Mapping(source = "fileGrpCodeId", target = "fileGrpCode.codeId")
    AtchFile toEntity(AtchFileRequest request);

    @Mapping(source = "fileGrpCode.codeId", target = "fileGrpCodeId")
    @Mapping(source = "fileGrpCode.codeName", target = "fileGrpCodeName")
    AtchFileResponse toResponse(AtchFile entity);

    List<AtchFileResponse> toResponseList(List<AtchFileRequest> atchFiles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AtchFileRequest request, @MappingTarget AtchFile entity);
}