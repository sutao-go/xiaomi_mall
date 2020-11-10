package com.imooc.controller;
import com.google.code.kaptcha.Producer;
import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONObject;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
/**这个模块主要是用来处理比如像用户登录页面等的一系列操作
 * @author sutao
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    String verifyCode;
    String sessionData;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(method = RequestMethod.GET,
            value = "/index")
    public void index(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/index.html");
        InputStream in = resource.getInputStream();
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区中
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭
        in.close();
        out.close();
    }

    @RequestMapping(method=RequestMethod.GET,
            value="/login")
    public void login(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/login_page.html");
        InputStream in = resource.getInputStream();
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区中
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭
        in.close();
        out.close();
    }

    @RequestMapping(method = RequestMethod.POST,value="/login")
    @ResponseBody
    public Map<String,String> login1(
            @RequestParam Map<String,String> info,
            HttpServletRequest request
            ) throws Exception {
        Object name = info.get("accountnumber");
        String userName = name.toString();
        Object name1 = info.get("password");
        String passWord = name1.toString();
        Object name2 = info.get("kaptcha");
        String kaptcha = name2.toString();
        if (StringUtils.isEmpty(userName)) {
            info.put("resultCode", "100");
        }
        if (StringUtils.isEmpty(kaptcha)) {
            info.put("resultCode", "100");
        }
        if (StringUtils.isEmpty(passWord)) {
            info.put("resultCode", "100");
        }
        if (userName.length() != 0 && passWord.length() != 0 && kaptcha.length() != 0) {
            if (kaptcha.equals(verifyCode)) {
                //查询用户是否存在
                AdminUser user = adminUserService.login(userName, passWord);
                //如果用户存在
                if (user != null) {
                    String a = adminUserService.find(userName).toString();
                    /*这个是通过mybatis从数据库中取出来的数据{userName='123456', passWord='1234567'}*/
                    String b = a.substring(9);
                    JSONObject jsonObject = JSONObject.fromObject(b);
                    System.out.println(jsonObject);
                    String userName1 = jsonObject.getString("userName");
                    sessionData = userName1;
                    String passWord1 = jsonObject.getString("passWord");
                    String status  = jsonObject.getString("status");
                    if (userName1.equals(userName) &&
                        passWord.equals(passWord1) &&
                        status.equals("正常")
                    ){
                        request.getSession().setAttribute("userName",userName1);
                        System.out.println(verifyCode);
                        info.put("resultCode","200");
                        return info;
                    }if (userName1.equals(userName) && passWord.equals(passWord1) && status.equals("禁用")){
                        info.put("resultCode","303");
                        return info;
                    }else{
                        info.put("resultCode","202");
                        return info;
                    }
                }else{
                    info.put("resultCode","203");
                    return info;
                }
            }else{
                info.put("resultCode","204");
                return info;
            }
        }else{
            info.put("resultCode","404");
            return info;
        }

    }
    /**
     *
     * @return
     * 跳转页面用的
     */
    @RequestMapping(method = RequestMethod.GET ,value="/registered")
    public void userRegistered(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/registered.html");
        InputStream in = resource.getInputStream();
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区中
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭
        in.close();
        out.close();
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


    @RequestMapping(method = RequestMethod.GET ,value = "/test")
    @ResponseBody
    public Map<String,String> test(
            @RequestParam Map<String,String> info,
            HttpSession session
    ) throws Exception {
            session.setAttribute("userName", sessionData);
            String a = session.getAttribute("userName").toString();
            System.out.println(a);
            info.put("sessionData",a);
            return info;
    }
}