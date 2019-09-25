package com.dubbo.dubbospringboot;

import com.dubbo.dubbospringboot.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBackage = "com.study.dubbospringboot.dubbo")
public class DubboSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboSpringbootApplication.class, args);
    }

}
