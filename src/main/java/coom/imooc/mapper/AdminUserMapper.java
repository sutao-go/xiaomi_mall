
package coom.imooc.mapper;

import coom.imooc.entity.AdminUser;
import coom.imooc.entity.SalesManagement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sutao
 */
@Repository
public interface AdminUserMapper {

    AdminUser login(@Param("userName") String userName,@Param("passWord") String passWord);

    AdminUser insert(@Param("userName") String userName,@Param("passWord") String passWord);

    AdminUser find(@Param("userName")String userName);

    AdminUser findAdministrator(@Param("userName")String userName,@Param("password")String password);

    List<SalesManagement> findUserStatus(String userName, String userStatus);

    /**
     * 通过后台的管理员改变前台用户的账号权限用的
     * @param disableAccount
     * @param id
     * @return
     */
    int changeUserStatus(@Param("disableAccount")String disableAccount,@Param("id")String id);

    /**
     * 修改后台管理员的账号密码
     * @param newpassword
     * @param userName
     * @return
     */
    int changePassword(@Param("newpassword")String newpassword,@Param("userName")String userName);
}