package com.imooc.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 跳转到支付页面
 */
@Controller

@RequestMapping(value = "/payment")
public class paymentController {

    @RequestMapping(method = RequestMethod.GET,value = "/paymentWEB")
    public void paymentWEB(
            HttpServletResponse response
    ) throws IOException {
        ClassPathResource resource = new ClassPathResource("/main/webapp/templates/frontPage/payment.html");
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
}
