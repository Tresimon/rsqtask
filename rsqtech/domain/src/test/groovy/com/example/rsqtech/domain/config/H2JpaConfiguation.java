package com.example.rsqtech.domain.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.rsqtech.domain")
@EntityScan("com.example.rsqtech.domain")
@EnableAutoConfiguration
public class H2JpaConfiguation {

}

