package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author sutao
 */
@SpringBootApplication
@MapperScan("com.imooc.mapper")
public class XiaomiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomiApplication.class, args);
    }
}
