package com.base.infra.persistence.code;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.domain.code.Code;

public interface JpaCodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {

    Optional<Code> findByCode(String code);

    boolean existsByCode(String code);

    List<Code> findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(Long upperCodeId);

    List<Code> findByUpperCode_CodeId(Long upperCodeId);

    List<Code> findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(String upperCode);
}
