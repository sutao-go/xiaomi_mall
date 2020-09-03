package com.imooc.mall.service;


import com.imooc.mall.entity.adminUser;

import java.security.NoSuchAlgorithmException;
public interface adminUserService {
    /**
     *用来登录的
     * @param userName 这个是用户名
     * @param passWord 这个是密码
     * @return login() 这个是方法是用来登录的
     */
    adminUser login(String userName, String passWord);
}
