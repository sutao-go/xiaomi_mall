
package com.imooc.service;
import com.imooc.entity.AdminUser;
import com.imooc.entity.SalesManagement;

import java.util.List;

public interface AdminUserService {
    /**
     *用来登录的
     * @param userName 这个是用户名
     * @param passWord 这个是密码
     * @return login() 这个是方法是用来登录的
     */
    AdminUser login(String userName, String passWord);

    /**
     * 用来注册用户的
     * @param userName 注册用户名
     * @param passWord 注册密码
     * @return
     */
    AdminUser registered(String userName,String passWord);

    AdminUser find(String userName);

    AdminUser findAdministrator(String userName, String password);

    /**
     * 仅按用户名查询后台管理员（修改密码时用于校验旧密码）
     */
    AdminUser findAdminByName(String userName);

    List<SalesManagement> findUserStatus(String userName, String userStatus);

    int changeUserStatus(String disableAccount,String id);

    int changePassword(String newpassword, String userName);
}

