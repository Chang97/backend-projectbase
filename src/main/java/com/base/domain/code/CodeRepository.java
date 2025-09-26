package com.base.domain.code;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {

    Optional<Code> findByCode(String code);
    
}
