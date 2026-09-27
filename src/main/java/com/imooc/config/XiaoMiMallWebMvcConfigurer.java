package com.imooc.config;

import com.imooc.interceptor.AdminLoginInterceptor;
import com.imooc.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * <p>
 * 修复：原 addInterceptors() 中 4 个拦截器的注册代码被整体注释，导致后台接口完全无鉴权。
 * 这里恢复并采用“白名单放行、其余拦截”的模式。
 */
@Configuration
public class XiaoMiMallWebMvcConfigurer implements WebMvcConfigurer {

    /** 无需登录即可访问的路径 */
    private static final String[] WHITELIST = {
            "/admin/login",
            "/admin/registered",
            "/admin/kaptcha",
            "/backendLogin/login"
    };

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 前台用户相关接口：必须登录
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**", "/user/**", "/orderlist/**", "/cart/**")
                .excludePathPatterns(WHITELIST);

        // 后台管理接口：必须管理员登录（只放行登录接口本身）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/backendLogin/**")
                .excludePathPatterns("/backendLogin/login");
    }

    /**
     * 运行时上传目录映射（与 BackendLogin.UPLOAD_DIR 保持一致），
     * 使上传成功的图片可通过 /resources/upload/xxx.png 访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = System.getProperty("user.dir") + "/upload/";
        registry.addResourceHandler("/resources/upload/**")
                .addResourceLocations("file:" + uploadDir);
    }
}
