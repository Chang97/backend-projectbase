package com.base.shared.code.adapter.out.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.shared.code.adapter.out.persistence.entity.CodeEntity;


public interface CodeJpaRepository extends JpaRepository<CodeEntity, Long>, JpaSpecificationExecutor<CodeEntity> {

    Optional<CodeEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<CodeEntity> findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(Long upperCodeId);

    List<CodeEntity> findByUpperCode_CodeId(Long upperCodeId);

    List<CodeEntity> findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(String upperCode);
}
