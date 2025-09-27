package com.base.api.menu.mapper;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.domain.menu.Menu;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(source = "upperMenuId", target = "upperMenu.menuId")
    Menu toEntity(MenuRequest request);

    @Mapping(source = "upperMenu.menuId", target = "upperMenuId")
    MenuResponse toResponse(Menu menu);

    List<MenuResponse> toResponseList(List<Menu> menus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(MenuRequest request, @MappingTarget Menu entity);
}
