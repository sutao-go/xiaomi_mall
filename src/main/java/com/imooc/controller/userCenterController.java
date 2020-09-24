package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class userCenterController {

    @RequestMapping(method = RequestMethod.GET,value = "/center")
    public String userCenter(){
        return "templates/frontPage/userCenter";
    }

}
