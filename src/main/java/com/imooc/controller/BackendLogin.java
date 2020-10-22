package com.imooc.controller;

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
@RequestMapping("/backendLogin")
public class BackendLogin {
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login() {
        return "templates/backgroundPage/BackgroundLogin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Map<String, String> doPostXiaomi10(
            @RequestParam Map<String, String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception {

        return info;
    }
}
