package com.base.domain.org;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepository extends JpaRepository<Org, Long> {
    Optional<Org> findByOrgCode(String orgCode);

    boolean existsByOrgCode(String orgCode);

    // 상위 코드 기준 하위코드 조회
    List<Org> findByUpperOrg_OrgIdAndUseYnTrueOrderBySrtAsc(Long upperOrgId);

    // 그룹코드 문자열로 바로 하위 조회도 가능하게
    List<Org> findByUpperOrg_OrgAndUseYnTrueOrderBySrtAsc(String upperOrg);

}
