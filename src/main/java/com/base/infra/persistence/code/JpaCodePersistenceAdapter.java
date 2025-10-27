package com.base.infra.persistence.code;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.usecase.query.condition.CodeSearchCondition;
import com.base.domain.code.Code;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaCodePersistenceAdapter implements CodePersistencePort {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.asc("orderPath"));

    private final JpaCodeRepository codeRepository;

    @Override
    public Code save(Code code) {
        return codeRepository.save(code);
    }

    @Override
    public Optional<Code> findById(Long codeId) {
        return codeRepository.findById(codeId);
    }

    @Override
    public Optional<Code> findByCode(String code) {
        return codeRepository.findByCode(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return codeRepository.existsByCode(code);
    }

    @Override
    public List<Code> findChildrenByUpperId(Long upperCodeId) {
        return codeRepository.findByUpperCode_CodeId(upperCodeId);
    }

    @Override
    public List<Code> findActiveChildrenByUpperId(Long upperCodeId) {
        return codeRepository.findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(upperCodeId);
    }

    @Override
    public List<Code> findActiveChildrenByUpperCode(String upperCode) {
        return codeRepository.findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(upperCode);
    }

    @Override
    public List<Code> search(CodeSearchCondition condition) {
        CodeSearchCondition criteria = condition != null ? condition : new CodeSearchCondition();
        criteria.normalize();
        return codeRepository.findAll(CodeSpecifications.withCondition(criteria), DEFAULT_SORT);
    }
}
