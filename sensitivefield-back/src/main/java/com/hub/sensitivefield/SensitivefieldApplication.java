package com.hub.sensitivefield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:project.properties")
public class SensitivefieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensitivefieldApplication.class, args);
    }

}
