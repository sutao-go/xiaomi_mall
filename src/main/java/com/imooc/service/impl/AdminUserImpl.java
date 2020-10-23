
package com.imooc.service.impl;


import com.imooc.service.AdminUserService;
import com.imooc.mapper.AdminUserMapper;
import com.imooc.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sutao
 */
@Service
public class AdminUserImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public AdminUser login(String userName, String passWord) {

        return adminUserMapper.login(userName,passWord);
    }
    @Override
    public AdminUser registered(String userName,String passWord)
    {
        return adminUserMapper.insert(userName,passWord);
    }
    @Override
    public AdminUser find(String userName){
        return adminUserMapper.find(userName);
    }

    @Override
    public AdminUser findAdministrator(String userName, String password) {
        return adminUserMapper.findAdministrator(userName,password);
    }
}
