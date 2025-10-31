package com.base.contexts.code.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.code.adapter.out.persistence.entity.CodeEntity;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.model.CodeId;

@Component
public class CodeEntityMapper {

    public CodeEntity toNewEntity(Code domain) {
        CodeEntity entity = new CodeEntity();
        apply(domain, entity);
        return entity;
    }

    public void merge(Code domain, CodeEntity target) {
        // 감사필드(createdId 등)는 target에 이미 들어 있으므로 건드리지 않습니다.
        apply(domain, target);
    }

    private void apply(Code domain, CodeEntity target) {
        target.setCode(domain.getCode());
        target.setCodeName(domain.getCodeName());
        target.setDescription(domain.getDescription());
        target.setSrt(domain.getSrt());
        target.setOrderPath(domain.getOrderPath());
        target.setEtc1(domain.getEtc1());
        target.setEtc2(domain.getEtc2());
        target.setEtc3(domain.getEtc3());
        target.setEtc4(domain.getEtc4());
    }

    public Code toDomain(CodeEntity entity) {
        return Code.restore(
            CodeId.of(entity.getCodeId()),
            entity.getUpperCode() != null ? CodeId.of(entity.getUpperCode().getCodeId()) : null,
            entity.getCode(),
            entity.getCodeName(),
            entity.getDescription(),
            entity.getSrt(),
            entity.getOrderPath(),
            Boolean.TRUE.equals(entity.getUseYn()),
            entity.getEtc1(),
            entity.getEtc2(),
            entity.getEtc3(),
            entity.getEtc4()
        );
  }
}
