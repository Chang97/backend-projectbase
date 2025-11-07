package com.base.contexts.code.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.contexts.code.adapter.out.persistence.entity.CodeEntity;
import com.base.contexts.code.adapter.out.persistence.mapper.CodeEntityMapper;
import com.base.contexts.code.adapter.out.persistence.repo.CodeJpaRepository;
import com.base.contexts.code.adapter.out.persistence.spec.CodeEntitySpecifications;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.model.CodeFilter;
import com.base.contexts.code.domain.model.CodeId;
import com.base.contexts.code.domain.model.CodeSnapshot;
import com.base.contexts.code.domain.port.out.CodeCommandPort;
import com.base.contexts.code.domain.port.out.CodeReferencePort;
import com.base.contexts.code.domain.port.out.CodeQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class CodeRepositoryAdapter implements CodeCommandPort, CodeQueryPort, CodeReferencePort {

    private final CodeJpaRepository jpaRepository;
    private final CodeEntityMapper mapper;

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public Code save(Code code) {
        CodeEntity entity;
        if (code.getCodeId() == null) {
            entity = mapper.toNewEntity(code);
        } else {
            entity = jpaRepository.findById(code.getCodeId().codeId())
                    .orElseThrow(() -> new NotFoundException("Code not found"));
            mapper.merge(code, entity);
        }
        if (code.getUpperCodeId() != null) {
            CodeEntity parent = jpaRepository.getReferenceById(code.getUpperCodeId().codeId());
            entity.setUpperCode(parent);
        } else {
            entity.setUpperCode(null);
        }
        CodeEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Code> findById(Long codeId) {
        return jpaRepository.findById(codeId).map(mapper::toDomain);
    }

    @Override
    public Optional<Code> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<Code> findChildrenByUpperId(Long upperCodeId) {
        return jpaRepository.findByUpperCode_CodeId(upperCodeId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Code> findActiveChildrenByUpperId(Long upperCodeId) {
        return jpaRepository.findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(upperCodeId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Code> findActiveChildrenByUpperCode(String upperCode) {
        return jpaRepository.findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(upperCode).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Code> search(CodeFilter filter) {
        return jpaRepository.findAll(CodeEntitySpecifications.withFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<CodeSnapshot> load(CodeId codeId) {
        if (codeId == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(codeId.codeId())
                .map(entity -> new CodeSnapshot(CodeId.of(entity.getCodeId()), entity.getOrderPath()));
    }
}
