package com.niiazov.coursemanagement.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootConfiguration
@EnableJpaAuditing
@EnableCaching
public class AppConfig {
}
