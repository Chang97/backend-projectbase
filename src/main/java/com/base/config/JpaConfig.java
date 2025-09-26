package com.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.base.domain") // JPA 리포지토리 위치
@EnableJpaAuditing // createdDt, updatedDt 자동 관리하려면 필요
public class JpaConfig {
    // 보통 여기서는 특별히 커스터마이징할 게 많지 않음
    // 공통 BaseEntity 만들어서 @CreatedDate, @LastModifiedDate 적용 가능
}
