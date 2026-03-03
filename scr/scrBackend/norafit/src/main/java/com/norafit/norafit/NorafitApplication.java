package com.norafit.norafit;

import com.norafit.norafit.console.consoleApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NorafitApplication {

    public static void main(String[] args) {
        SpringApplication.run(NorafitApplication.class, args);
    }

    @Bean
    CommandLineRunner run(consoleApp consoleApp) {
        return args -> consoleApp.start();
    }
}