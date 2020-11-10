package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sutao
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.imooc.*"})
@MapperScan(basePackages = {"com.imooc.mapper","resources.mapper.AdminUserMapper.xml"})
public class XiaomiApplication {
    public static void main(String[] args){
        SpringApplication.run(XiaomiApplication.class,args);
    }
}
