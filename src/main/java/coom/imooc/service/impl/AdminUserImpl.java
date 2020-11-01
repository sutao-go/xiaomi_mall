
package coom.imooc.service.impl;


import coom.imooc.entity.SalesManagement;
import coom.imooc.service.AdminUserService;
import coom.imooc.mapper.AdminUserMapper;
import coom.imooc.entity.AdminUser;
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
