package com.imooc.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.imooc.common.BizException;
import com.imooc.common.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 前台用户登录拦截器（基于 Sa-Token）
 * <p>
 * 页面类 GET 请求未登录时跳转登录页，接口类请求返回 401 JSON。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PAGE = "/templates/frontPage/login_page.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StpUtil.isLogin()) {
            return true;
        }
        // 页面跳转：重定向到登录页（体验优先）
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return false;
        }
        // 接口请求：返回统一 401
        throw new BizException(ResultCode.UNAUTHORIZED);
    }
}
