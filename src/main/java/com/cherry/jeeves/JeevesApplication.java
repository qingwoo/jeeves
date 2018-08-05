package com.cherry.jeeves;

import com.cherry.jeeves.controller.BotController;
import com.cherry.jeeves.service.LoginService;
import com.cherry.jeeves.service.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JeevesApplication {

    private static final Logger logger = LoggerFactory.getLogger(JeevesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JeevesApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty("jeeves.instance-id")
    public BotController botController() {
        return new BotController();
    }

    @Bean
    @ConditionalOnMissingBean(value = MessageHandler.class)
    public MessageHandler messageHandler() {
        return new MessageHandlerImpl();
    }

    @Bean
    @ConditionalOnProperty("jeeves.instance-id")
    public Jeeves jeeves(@Value("${jeeves.instance-id}") String instanceId, LoginService loginService) {
        return new Jeeves(instanceId, loginService);
    }

    @Bean
    public CommandLineRunner run(@Autowired(required = false) Jeeves jeeves) throws Exception {
        if (jeeves == null) { return null; }
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.error(e.getMessage(), e);
            System.exit(1);
        });
        return args -> jeeves.start();
    }
}
