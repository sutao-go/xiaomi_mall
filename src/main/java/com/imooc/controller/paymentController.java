package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/payment")
public class paymentController {
    @RequestMapping(method = RequestMethod.GET,value = "/paymentWEB")
    public String paymentWEB(){
        return "templates/frontPage/payment";
    }
}
