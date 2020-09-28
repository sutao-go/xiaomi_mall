
package com.imooc.mapper;

import com.imooc.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCenterMapper {
    int changePassword(@Param("passWord")String passWord,@Param("userName")String userName);
}