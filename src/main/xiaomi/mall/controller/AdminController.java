package main.xiaomi.mall.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController {
    @RequestMapping(value = "/index")
    public String index(){
        System.out.println("success");
        return "templates/backgroundPage/sidebar.html";
    }
}
