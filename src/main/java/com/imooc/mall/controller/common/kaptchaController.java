package com.imooc.mall.controller.common;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/kaptcha")
public class kaptchaController {
    @Autowired
    private Producer kaptchaProducer;
    @RequestMapping("/index")
    public void  getKaptchaImage(HttpServletResponse response, HttpServletRequest request)
            throws Exception{
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try{
            //生产验证码字符串并保存到session中
            String verifyCode = kaptchaProducer.createText();
            request.getSession().setAttribute("verifyCode",verifyCode);
            BufferedImage challenge = kaptchaProducer.createImage(verifyCode);
            ImageIO.write(challenge,"jpg",imgOutputStream);
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Parama","no-Cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
