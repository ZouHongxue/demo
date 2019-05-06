package com.dubbo.consumer;

import com.dubbo.sample.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class ConsumerApplication {
    @Reference(version = "1.0.0", url = "dubbo://0.0.0.0:9393")
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            System.out.println(demoService.sayHello("abcdefg"));
        };
    }
}
