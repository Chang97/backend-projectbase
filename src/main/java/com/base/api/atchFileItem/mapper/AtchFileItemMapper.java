package com.base.api.atchFileItem.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.domain.atchFileItem.AtchFileItem;

@Mapper(componentModel = "spring")
public interface AtchFileItemMapper {

    AtchFileItem toEntity(AtchFileItemRequest request);

    AtchFileItemResponse toResponse(AtchFileItem entity);

    List<AtchFileItemResponse> toResponseList(List<AtchFileItemRequest> AtchFileItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AtchFileItemRequest request, @MappingTarget AtchFileItem entity);
}