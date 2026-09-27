package com.imooc.interceptor;

import com.imooc.common.BizException;
import com.imooc.common.ResultCode;
import com.imooc.common.SessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户登录拦截器
 * <p>
 * 合并了原 FrontEndSystemAuthenticationInterceptor / PersonalCenterIntercepter / PayIntercepter
 * 三个几乎完全重复的拦截器。
 * 页面类 GET 请求未登录时跳转登录页，接口类请求返回 401 JSON。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PAGE = "/templates/frontPage/login_page.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (SessionUtil.getUserName(session) != null) {
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
