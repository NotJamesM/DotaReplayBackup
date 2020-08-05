package com.github.notjamesm.DotaReplayBackup.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
@Getter
public class HttpClientConfig {

    @Value("${opendota.host}")
    private String dotaApiHost;

    @Bean
    HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
