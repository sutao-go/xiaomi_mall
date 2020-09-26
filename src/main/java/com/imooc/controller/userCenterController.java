package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class userCenterController {
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

}
