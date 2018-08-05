package com.cherry.jeeves;

import com.cherry.jeeves.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JeevesApplication {

    private static final Logger logger = LoggerFactory.getLogger(JeevesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JeevesApplication.class, args);
    }

    @Bean
    public Jeeves jeeves(@Value("${jeeves.instance-id}") String instanceId, LoginService loginService) {
        return new Jeeves(instanceId, loginService);
    }

    @Bean
    public CommandLineRunner run(Jeeves jeeves) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.error(e.getMessage(), e);
            System.exit(1);
        });
        return args -> jeeves.start();
    }
}
