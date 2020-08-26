package com.github.notjamesm.DotaReplayBackup.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    Logger logger() {
        return LoggerFactory.getLogger("DotaReplayBackup");
    }
}
