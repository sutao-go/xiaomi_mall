package com.imooc.mall.dao;

import com.imooc.mall.entity.adminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "adminUserDao")
public interface adminUserDao {
    adminUser login(@Param("userName") String userName, @Param("passWord") String passWord);
}
