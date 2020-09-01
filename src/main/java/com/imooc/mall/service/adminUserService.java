package com.imooc.mall.service;


import com.imooc.mall.entity.adminUser;

import java.security.NoSuchAlgorithmException;
public interface adminUserService {
    /*
   用来登录的
     */
    adminUser login(String userName, String passWord) throws NoSuchAlgorithmException;
}
