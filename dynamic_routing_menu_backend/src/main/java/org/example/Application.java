package org.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 关键注解
@SpringBootApplication
@MapperScan(value = "org.example.mapper")
public class Application {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SpringApplication.run(Application.class, args);
    }
}