package com.imooc.controller;

import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    AdminUserService adminUserService;
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login() {
        return "templates/backgroundPage/BackgroundLogin";
    }

    /**
     * 实现后台登录页面的跳转的
     * @param info
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Map<String, String> doPostXiaomi10(
            @RequestParam Map<String, String> info,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception {
        Object name =info.get("accountnumber");
        String userName = name.toString();
        Object name2 = info.get("password");
        String password = name2.toString();
        AdminUser adminUser = adminUserService.findAdministrator(userName,password);
        JSONObject adminUser1 = JSONObject.fromObject(adminUser);
        String adminUserName = adminUser1.getString("userName");
        String adminPassWord = adminUser1.getString("passWord");
        if (userName.equals(adminUserName)&&password.equals(adminPassWord)){
            info.put("resultCode","200");
            System.out.println("success");
            return info;
        }else{
            System.out.println("您的账号和密码可能出现了一些问题，建议您可以检查下您的账号和密码");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/index")
    public String index(){
        return "templates/backgroundPage/layuiadmin/src/views/index";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/index")
    public Map<String,String> index(
            @RequestParam Map<String,String> info,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request
    )throws Exception{
        return info;
    }
}
