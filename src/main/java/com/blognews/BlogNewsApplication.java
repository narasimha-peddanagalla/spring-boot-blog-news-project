package com.blognews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogNewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogNewsApplication.class, args);
        System.out.println("BlogNews Spring Boot app started successfully...");
    }


}
