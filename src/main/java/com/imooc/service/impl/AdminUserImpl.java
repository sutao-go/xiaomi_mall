
package com.imooc.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.imooc.entity.SalesManagement;
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
        // 用 LambdaQueryWrapper 替代 XML 里的 select * from adminuser where user_name=?
        // 好处：字段名由方法引用（AdminUser::getUserName）表达，改字段名时编译期就会报错
        LambdaQueryWrapper<AdminUser> query = new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUserName, userName)
                .last("limit 1");
        return adminUserMapper.selectOne(query);
    }

    @Override
    public AdminUser findAdministrator(String userName, String password) {
        return adminUserMapper.findAdministrator(userName,password);
    }

    @Override
    public AdminUser findAdminByName(String userName) {
        return adminUserMapper.findAdminByName(userName);
    }

    @Override
    public List<SalesManagement> findUserStatus(String userName, String userStatus) {
        return adminUserMapper.findUserStatus(userName,userStatus);
    }

    @Override
    public int changeUserStatus(String disableAccount,String id) {
        return adminUserMapper.changeUserStatus(disableAccount,id);
    }

    @Override
    public int changePassword(String newpassword, String userName) {
        return adminUserMapper.changePassword(newpassword,userName);
    }
}
