package com.rookies6.MySpringBootLabProject.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${spring.application.name}")
    String applicationName;

    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Logger 구현체 클래스명 {}", logger.getClass().getName());
        logger.info("MyRunner run() 호출됨!!");
        logger.info("UserName = {}", username);
        logger.info("port = {}", port);
    }
}
