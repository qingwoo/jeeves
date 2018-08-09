package com.cherry.jeeves;

import com.cherry.jeeves.controller.BotController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
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

//    @Bean
//    @ConditionalOnProperty("jeeves.instance-id")
//    public Jeeves jeeves(@Value("${jeeves.instance-id}") String instanceId, LoginService loginService) {
//        return new Jeeves(instanceId, loginService);
//    }
//
//    @Bean
//    public CommandLineRunner run(@Autowired(required = false) Jeeves jeeves) throws Exception {
//        if (jeeves == null) { return null; }
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            logger.error(e.getMessage(), e);
//            System.exit(1);
//        });
//        return args -> jeeves.start();
//    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        return taskExecutor;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(Executor taskExecutor) {
        SimpleApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        applicationEventMulticaster.setTaskExecutor(taskExecutor);
        return applicationEventMulticaster;
    }

}
