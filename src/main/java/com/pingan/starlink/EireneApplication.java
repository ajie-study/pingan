package com.pingan.starlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.pingan.starlink.mapper")
public class EireneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EireneApplication.class, args);
    }

}

