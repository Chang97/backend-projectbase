package com.base.api.org.mapper;

import com.base.api.org.dto.OrgRequest;
import com.base.api.org.dto.OrgResponse;
import com.base.domain.org.Org;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrgMapper {

    @Mapping(source = "upperOrgId", target = "upperOrg.orgId")
    Org toEntity(OrgRequest request);

    @Mapping(source = "upperOrg.orgId", target = "upperOrgId")
    OrgResponse toResponse(Org org);

    List<OrgResponse> toResponseList(List<Org> orgs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(OrgRequest request, @MappingTarget Org entity);
}
