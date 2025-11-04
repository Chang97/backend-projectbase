package com.base.contexts.organization.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.organization.adapter.out.persistence.entity.OrgEntity;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.model.OrgId;

@Component
public class OrgEntityMapper {

    public Org toDomain(OrgEntity entity) {
        if (entity == null) {
            return null;
        }
        return Org.restore(
                OrgId.of(entity.getOrgId()),
                entity.getUpperOrg() != null ? OrgId.of(entity.getUpperOrg().getOrgId()) : null,
                entity.getOrgCode(),
                entity.getOrgName(),
                entity.getSrt(),
                entity.getUseYn(),
                entity.getCreatedId(),
                entity.getUpdatedId(),
                entity.getCreatedDt(),
                entity.getUpdatedDt()
        );
    }

    public OrgEntity toNewEntity(Org org, OrgEntity upperOrg) {
        return OrgEntity.builder()
                .upperOrg(upperOrg)
                .orgCode(org.getOrgCode())
                .orgName(org.getOrgName())
                .srt(org.getSrt())
                .useYn(org.getUseYn())
                .build();
    }

    public void merge(Org org, OrgEntity entity, OrgEntity upperOrg) {
        entity.setUpperOrg(upperOrg);
        entity.setOrgCode(org.getOrgCode());
        entity.setOrgName(org.getOrgName());
        entity.setSrt(org.getSrt());
        entity.setUseYn(org.getUseYn());
    }
}
