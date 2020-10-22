package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/settlement")
public class CommoditySettlementController {

    @RequestMapping("/shoppingCart")
    public String shoppingCart(){
        return null;
    }

}
