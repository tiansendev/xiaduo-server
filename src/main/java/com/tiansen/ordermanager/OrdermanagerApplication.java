package com.tiansen.ordermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OrdermanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdermanagerApplication.class, args);
    }

}

