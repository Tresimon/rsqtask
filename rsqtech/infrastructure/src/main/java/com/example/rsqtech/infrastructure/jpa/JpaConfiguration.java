package com.example.rsqtech.infrastructure.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({"com.example.rsqtech.domain"})
@EntityScan({"com.example.rsqtech.domain"})
class JpaConfiguration {

}
