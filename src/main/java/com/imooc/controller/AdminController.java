package com.imooc.controller;
import com.google.code.kaptcha.Producer;
import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserService;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
/**
 * @author sutao
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    String verifyCode;
    @Autowired
    private AdminUserService adminUserService;
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
            HttpServletResponse response,
            HttpSession session
            ) throws Exception {
        Object name =info.get("accountnumber");
        String userName = name.toString();
        Object name1 =info.get("password");
        String passWord = name1.toString();
        Object name2 =info.get("kaptcha");
        String kaptcha = name2.toString();
        System.out.println(info);
        if (StringUtils.isEmpty(userName)){
            info.put("resultCode","100");
        }if (StringUtils.isEmpty(kaptcha)){
            info.put("resultCode","100");
        }
        if (StringUtils.isEmpty(passWord)) {
            info.put("resultCode","100");
        }
        if(userName.length() != 0 && passWord.length() != 0 && kaptcha.length() != 0){
            System.out.println("这是验证码"+verifyCode);
                if (kaptcha.equals(verifyCode)){
                    AdminUser user = adminUserService.login(userName,passWord);
                    if (user != null){
                        info.put("resultCode","200");
                    }else{
                        info.put("resultCode","202");
                    }
                }else{
                    info.put("resultCode","204");
                }
        }else{
            System.out.println("AdminController的"+"/login"+"出大问题了");
        }
        return info;
    }

    /**
     *
     * @return
     * 跳转页面用的
     */
    @RequestMapping(method = RequestMethod.GET ,value="/registered")
    public String userRegistered(){
        return "templates/frontPage/registered";
    }
    /**
     * 注册用户用的
     */
    @RequestMapping(method = RequestMethod.POST,value = "/registered")
    @ResponseBody
    public  Map<String, String> registered(
            @RequestParam Map<String,String> info,
            HttpServletResponse response,
            HttpSession session
    ){
        Object name =info.get("accountnumber");
        String userName = name.toString();
        System.out.println(userName);
        Object name1 = info.get("password");
        String passWord = name1.toString();
        if (userName.length() != 0 && passWord.length() != 0){
                AdminUser user = adminUserService.registered(userName,passWord);
                info.put("resultCode","206");
        }else{
            info.put("resultCode","208");
        }
        return info;
    }
    /**
     *
     * @param response
     * @param request
     * @throws Exception
     * 搞定验证码用的
     */
    @RequestMapping("/kaptcha")
    public String  getKaptchaImage(HttpServletResponse response, HttpServletRequest request)
            throws Exception{
            byte[] captchaOutputStream = null;
            ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
            //生产验证码字符串并保存到session中
             verifyCode = kaptchaProducer.createText();
            System.out.println(verifyCode);
            request.getSession().setAttribute("verifyCode",verifyCode);
            BufferedImage challenge = kaptchaProducer.createImage(verifyCode);
            ImageIO.write(challenge,"jpg",imgOutputStream);
            captchaOutputStream = imgOutputStream.toByteArray();
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Parama","no-Cache");
            response.setDateHeader("Expires",0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(captchaOutputStream);
            responseOutputStream.flush();
            responseOutputStream.close();
            return verifyCode;
    }
}