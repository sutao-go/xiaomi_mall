package com.imooc.controller;

import com.google.code.kaptcha.Producer;
import com.imooc.common.BizException;
import com.imooc.common.Result;
import com.imooc.common.ResultCode;
import com.imooc.common.SessionUtil;
import com.imooc.entity.AdminUser;
import com.imooc.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 前台用户：登录 / 注册 / 验证码 / 退出
 * <p>
 * 重构要点：
 * 1. 删除 Controller 可变成员变量（原 verifyCode / sessionData 会被所有请求共享）
 * 2. 删除后门接口 /admin/test（原接口可劫持任意用户会话）
 * 3. 验证码改存 session 且一次性消费（原存字段，可无限复用暴破）
 * 4. 密码改用 BCrypt 校验（原明文比对）
 * 5. 页面跳转改为重定向到静态资源（原手工拷贝文件流，160+ 处重复代码）
 *
 * @author sutao
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    /** 用户名规则：2-20 位中英文/数字/下划线，防止存储型 XSS */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,20}$");

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 首页（原实现为手工输出 html 流）
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/templates/frontPage/index.html";
    }

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/templates/frontPage/login_page.html";
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ResponseBody
    public Result<Void> login(@RequestParam Map<String, String> info, HttpSession session) {
        String userName = info.get("accountnumber");
        String passWord = info.get("password");
        String kaptcha = info.get("kaptcha");

        if (StringUtils.isAnyBlank(userName, passWord, kaptcha)) {
            throw new BizException(ResultCode.PARAM_ERROR, "账号、密码、验证码不能为空");
        }
        // 验证码一次性消费，校验后立即失效
        if (!SessionUtil.verifyKaptcha(session, kaptcha)) {
            throw new BizException(ResultCode.KAPTCHA_ERROR);
        }

        AdminUser user = adminUserService.find(userName);
        if (user == null || !passwordEncoder.matches(passWord, user.getPassWord())) {
            // 不区分“用户不存在/密码错误”，避免账号枚举
            throw new BizException(ResultCode.PARAM_ERROR, "账号或密码错误");
        }
        if ("禁用".equals(user.getStatus())) {
            throw new BizException(ResultCode.ACCOUNT_DISABLED);
        }
        SessionUtil.setUserName(session, user.getUserName());
        return Result.ok();
    }

    /**
     * 注册页
     */
    @GetMapping("/registered")
    public String registeredPage() {
        return "redirect:/templates/frontPage/registered.html";
    }

    /**
     * 用户注册
     */
    @PostMapping("/registered")
    @ResponseBody
    public Result<Void> registered(@RequestParam Map<String, String> info) {
        String userName = StringUtils.trimToEmpty(info.get("accountnumber"));
        String passWord = info.get("password");

        if (!USERNAME_PATTERN.matcher(userName).matches()) {
            throw new BizException(ResultCode.PARAM_ERROR, "用户名为 2-20 位字母、数字、下划线或中文");
        }
        if (StringUtils.length(passWord) < 6) {
            throw new BizException(ResultCode.PARAM_ERROR, "密码至少 6 位");
        }
        if (adminUserService.find(userName) != null) {
            throw new BizException(ResultCode.CONFLICT, "该账号已被注册");
        }
        // 入库前 BCrypt 加密，杜绝明文存储
        adminUserService.registered(userName, passwordEncoder.encode(passWord));
        return Result.ok();
    }

    /**
     * 验证码图片（文本存入 session，不落 Controller 字段）
     */
    @GetMapping("/kaptcha")
    public void kaptcha(HttpServletResponse response, HttpSession session) throws Exception {
        String text = kaptchaProducer.createText();
        SessionUtil.setKaptcha(session, text);

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        BufferedImage image = kaptchaProducer.createImage(text);
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ResponseBody
    public Result<Void> logout(HttpSession session) {
        SessionUtil.logout(session);
        return Result.ok();
    }
}
