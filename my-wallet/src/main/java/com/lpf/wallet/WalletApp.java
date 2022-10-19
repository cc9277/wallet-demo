package com.lpf.wallet;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
@MapperScan(basePackages = "com.lpf.wallet.mapper")
public class WalletApp {

    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class);
    }

}
