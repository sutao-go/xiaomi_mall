package com.imooc.controller;
import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/**
 * @author sutao
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminUserService adminUserService;
    @RequestMapping(method = RequestMethod.GET,value = "/index")
    public String index(){
        return "templates/frontPage/index";
    }
    @RequestMapping(method = RequestMethod.GET,value = "/login")
    public String login(){
        return "templates/frontPage/login_page";
    }
    @RequestMapping(method = RequestMethod.POST ,value = "/login")
    @ResponseBody
    public Map<String,String> login1(
            @RequestParam Map<String,String> info,
            HttpServletResponse response
            ) throws Exception {
        Object name =info.get("accountnumber");
        String userName = name.toString();
        Object name1 =info.get("password");
        String passWord = name1.toString();
        Object name2 =info.get("kaptcha");
        String kaptcha = name2.toString();
        System.out.println(info);
        if (StringUtils.isEmpty(userName)){
            info.put("resultCode","100");
        }if (StringUtils.isEmpty(kaptcha)){
            info.put("resultCode","100");
        }
        if (StringUtils.isEmpty(passWord)) {
            info.put("resultCode","100");
        }
        if(userName.length() != 0 && passWord.length() != 0 && kaptcha.length() != 0){
            AdminUser user = adminUserService.login(userName,passWord);
            System.out.println(user);
            if (user != null){
                info.put("resultCode","200");
            }
        }else{
            System.out.println("AdminController的"+"/login"+"出大问题了");
        }
        return info;
    }
}