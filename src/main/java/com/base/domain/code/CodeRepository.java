package com.base.domain.code;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {

    Optional<Code> findByCode(String code);

    boolean existsByCode(String code);

    // 상위 코드 기준 하위코드 조회
    List<Code> findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(Long upperCodeId);

    // 그룹코드 문자열로 바로 하위 조회도 가능하게
    List<Code> findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(String upperCode);

    List<Code> findByUpperCode_CodeId(Long upperCodeId);
    
}
