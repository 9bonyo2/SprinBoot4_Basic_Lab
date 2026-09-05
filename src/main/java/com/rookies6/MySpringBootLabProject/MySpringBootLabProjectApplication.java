package com.rookies6.MySpringBootLabProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class MySpringBootLabProjectApplication {

	public static void main(String[] args) {
        //SpringApplication.run(MySpringBoot4ProjectApplication.class, args);
        SpringApplication application = new SpringApplication(MySpringBootLabProjectApplication.class);
        //WebApplication type 변경
        //AnnotationConfigServletWebServerApplicationContext 컨테이너 객체가 생성됨.
        application.setWebApplicationType(WebApplicationType.SERVLET);
        //application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
	}

}
