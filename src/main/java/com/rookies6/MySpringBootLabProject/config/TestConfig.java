package com.rookies6.MySpringBootLabProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public MyEnvironment myEnvironment(){
        return MyEnvironment.builder()
                .mode("테스트환경")
                .rate(1.5)
                .build();
    }
}
