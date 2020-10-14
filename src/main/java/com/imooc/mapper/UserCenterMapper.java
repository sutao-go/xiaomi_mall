
package com.imooc.mapper;

import com.imooc.entity.AdminUser;
import com.imooc.entity.OrderList;
import com.imooc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCenterMapper {
    /**
     * mybatis中@param的顺序是会影响xml的执行的，不能乱放
     */
    int changePassword(@Param("userName")String userName,@Param("passWord")String passWord);


}