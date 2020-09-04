package com.imooc.mall.service.impl;
import com.imooc.mall.dao.AdminUserMapper;
import com.imooc.mall.entity.AdminUser;
import com.imooc.mall.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Override
    public AdminUser login(String userName, String passWord) {
        /*
           *@Autowired
           *private adminUserDao AdminUserDao;
           *千万不能写一个无用的注入，如果写了会导致以下错误
           *No qualifying bean of type [com.imooc.mall.dao.adminUserDao] found for dependency
           *在注入的时候全部使用@Autowired，不要用网上的@Repository会导致报错，
           *本来可以一次编译通过的，这个地方浪费了半天时间
         */
        String passwordMd5 = passWord +1;
        return adminUserMapper.login(userName,passwordMd5);
    }
}
