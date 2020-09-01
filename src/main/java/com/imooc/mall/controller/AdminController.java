package com.imooc.mall.controller;

import com.google.code.kaptcha.Producer;
import com.imooc.mall.service.adminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private adminUserService adminUserService;
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
            info.put("resultCode","200");
            System.out.println("1"+info);

        }else{
            info.put("resultCode","100");
            System.out.println("2"+info);
        }
        System.out.println("3"+info);
        return info;
    }
}