package com.imooc.interceptor;

import com.imooc.common.BizException;
import com.imooc.common.ResultCode;
import com.imooc.common.StpAdminUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 后台管理员登录拦截器（基于 Sa-Token 多账号体系）
 * <p>
 * 校验的是独立的 admin 账号体系，前台用户登录后无法进入后台。
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    private static final String ADMIN_LOGIN_PAGE = "/templates/backgroundPage/BackgroundLogin.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StpAdminUtil.isLogin()) {
            return true;
        }
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            response.sendRedirect(request.getContextPath() + ADMIN_LOGIN_PAGE);
            return false;
        }
        throw new BizException(ResultCode.UNAUTHORIZED);
    }
}
