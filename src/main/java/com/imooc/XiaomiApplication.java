package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author sutao
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.imooc.mapper","resources.mapper.AdminUserMapper.xml"})
public class XiaomiApplication {
    public static void main(String[] args){
        SpringApplication.run(XiaomiApplication.class,args);
    }
}
