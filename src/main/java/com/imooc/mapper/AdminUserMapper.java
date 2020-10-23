
package com.imooc.mapper;

import com.imooc.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sutao
 */
@Repository
public interface AdminUserMapper {

    AdminUser login(@Param("userName") String userName,@Param("passWord") String passWord);

    AdminUser insert(@Param("userName") String userName,@Param("passWord") String passWord);

    AdminUser find(@Param("userName")String userName);

    AdminUser findAdministrator(@Param("userName")String userName,@Param("password")String password);
}