package coom.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sutao
 */
@SpringBootApplication
@MapperScan(basePackages = {"coom.imooc.mapper","resources.mapper.AdminUserMapper.xml"})
public class XiaomiApplication {
    public static void main(String[] args){
        SpringApplication.run(XiaomiApplication.class,args);
    }
}
