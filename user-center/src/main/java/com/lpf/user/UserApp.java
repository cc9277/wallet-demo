package com.lpf.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.lpf.wallet.mapper"})
public class UserApp {

    public static void main(String[] args) {

        SpringApplication.run(UserApp.class);

    }

}
