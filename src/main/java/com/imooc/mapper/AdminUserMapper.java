
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

}