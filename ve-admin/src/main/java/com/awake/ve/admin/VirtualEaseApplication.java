package com.awake.ve.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.awake.ve")
@SpringBootApplication
public class VirtualEaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirtualEaseApplication.class, args);
    }
}
