package com.base.api.menu.mapper;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.domain.menu.Menu;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {

    @Mapping(target = "upperMenu", ignore = true)
    Menu toEntity(MenuRequest request);

    @Mapping(source = "upperMenu.menuId", target = "upperMenuId")
    MenuResponse toResponse(Menu menu);

    @Mapping(target = "upperMenu", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(MenuRequest request, @MappingTarget Menu entity);
}

