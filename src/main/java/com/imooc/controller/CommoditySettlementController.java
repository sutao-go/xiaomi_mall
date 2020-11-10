package com.imooc.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/settlement")
public class CommoditySettlementController {

    @RequestMapping("/shoppingCart")
    public String shoppingCart(){
        return null;
    }

}
