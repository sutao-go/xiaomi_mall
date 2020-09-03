package com.imooc.mall.controller;

import com.google.code.kaptcha.Producer;
import com.imooc.mall.entity.adminUser;
import com.imooc.mall.service.adminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author sutao
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private adminUserService AdminUserService;
    @Autowired
    private Producer kaptchaProducer;
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
        String accountnumber = name.toString();
        Object name1 =info.get("password");
        String password = name1.toString();
        Object name2 =info.get("kaptcha");
        String kaptcha = name2.toString();
        System.out.println(info);
        if (StringUtils.isEmpty(accountnumber)){
            info.put("resultCode","100");
        }if (StringUtils.isEmpty(kaptcha)){
            info.put("resultCode","100");
        }
        if (StringUtils.isEmpty(password)) {
            info.put("resultCode","100");
        }
        if(accountnumber.length() != 0 && password.length() != 0 && kaptcha.length() != 0){
            adminUser user = AdminUserService.login(accountnumber,password);
            if (user != null){
                info.put("resultCode","200");
            }
        }else{
            System.out.println("AdminController的"+"/login"+"出大问题了");
        }
        return info;
    }
}