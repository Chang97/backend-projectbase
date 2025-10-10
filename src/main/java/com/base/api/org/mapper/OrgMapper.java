package com.base.api.org.mapper;

import com.base.api.org.dto.OrgRequest;
import com.base.api.org.dto.OrgResponse;
import com.base.domain.org.Org;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrgMapper {

    @Mapping(target = "upperOrg", ignore = true)
    Org toEntity(OrgRequest request);

    @Mapping(source = "upperOrg.orgId", target = "upperOrgId")
    OrgResponse toResponse(Org org);
    
    @Mapping(target = "upperOrg", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(OrgRequest request, @MappingTarget Org entity);
}

