package com.imooc.controller;

import com.imooc.entity.AdminUser;
import com.imooc.service.UserCenterService;
import com.imooc.service.AdminUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private AdminUserService adminUserService;
    /**
     * 这个主要是用来实现前端页面跳转用的，跳转到个人用户中心用的
     */
    @RequestMapping(method = RequestMethod.GET,value = "/center")
    public String userCenter(){
        return "templates/frontPage/userCenter";
    }
    /**
     *这个主要是用来实现从个人中心的"修改个人信息"跳转到"修改密码这个功能上的"
     */
    @RequestMapping(method = RequestMethod.GET,value = "/accountManagement")
    public String userInformation(){
        return "templates/frontPage/accountManagement";
    }
    /**
     * 这个主要是实现修改密码功能的
     */
    @RequestMapping(method = RequestMethod.POST,value = "/accountManagement")
    @ResponseBody
    public Map<String,String> changePassword(
            @RequestParam Map<String,String> data,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
            ){
        Object name =data.get("oldPassword");
        String oldPassword = name.toString();
        Object name1 =data.get("newPassword1");
        String passWord = name1.toString();
        Object name2 =data.get("newPassword2");
        String newPassword2 = name2.toString();
        if (oldPassword.length() != 0 && passWord.length() != 0 && newPassword2.length() != 0){
            //登录成功之后通过session来获取账号
            Object attribute = request.getSession().getAttribute("userName");
            String userName = attribute.toString();
            String a = adminUserService.find(userName).toString();
            //将获取到的账号和密码发往后端的数据库中进行比对
            //将获取到的数据先转换成为jsonobject
            JSONObject b = JSONObject.fromObject(a);
            //获取json中的密码
            String name4 = b.getString("passWord");
            //将获取到的密码与前端传入的密码进行比对
            if (oldPassword.equals(name4)){
                //如果前后端密码一致执行下面的代码
                //先比对两次输入的新密码是否一致
                   if(name1.equals(name2)){
                       //如果两次输入的新密码一致就更改后台数据库中的密码
                       System.out.println("开始修改密码");
                       userCenterService.changePassword(userName,passWord);

                   }else{
                       //如果不一致执行以下的代码
                   }
            }else{
                System.out.println("false");
            }
        }
        return data;
    }
}
