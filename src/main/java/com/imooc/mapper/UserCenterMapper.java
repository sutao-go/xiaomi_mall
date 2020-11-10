
package com.imooc.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCenterMapper {
    /**
     * mybatis中@param的顺序是会影响xml的执行的，不能乱放
     */
    int changePassword(@Param("userName")String userName,@Param("passWord")String passWord);


}