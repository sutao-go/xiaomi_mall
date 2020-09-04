package com.imooc.mall.dao;

import com.imooc.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Param;


public interface AdminUserMapper {
    AdminUser login(@Param("userName") String userName, @Param("passWord") String passWord);
}
