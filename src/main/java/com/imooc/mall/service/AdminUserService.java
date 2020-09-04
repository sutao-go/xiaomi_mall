package com.imooc.mall.service;


import com.imooc.mall.entity.AdminUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface AdminUserService {
    /**
     *用来登录的
     * @param userName 这个是用户名
     * @param passWord 这个是密码
     * @return login() 这个是方法是用来登录的
     */
    AdminUser login(String userName, String passWord);
}
