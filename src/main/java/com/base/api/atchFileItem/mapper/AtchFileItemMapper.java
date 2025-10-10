package com.base.api.atchFileItem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.domain.atchFileItem.AtchFileItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AtchFileItemMapper {

    @Mapping(target = "atchFile", ignore = true)
    AtchFileItem toEntity(AtchFileItemRequest request);

    @Mapping(source = "atchFile.atchFileId", target = "atchFileId")
    AtchFileItemResponse toResponse(AtchFileItem entity);

    @Mapping(target = "atchFile", ignore = true)
    void updateEntityFromRequest(AtchFileItemRequest request, @MappingTarget AtchFileItem entity);
}
