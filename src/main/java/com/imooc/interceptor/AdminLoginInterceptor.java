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
 * 后台管理员登录拦截器
 * <p>
 * 原 BackendInterceptorConfiguration 校验的是前台 userName，
 * 导致任意前台登录用户即可操作后台；这里改为校验独立的 adminUserName。
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    private static final String ADMIN_LOGIN_PAGE = "/templates/backgroundPage/BackgroundLogin.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (SessionUtil.getAdminName(session) != null) {
            return true;
        }
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            response.sendRedirect(request.getContextPath() + ADMIN_LOGIN_PAGE);
            return false;
        }
        throw new BizException(ResultCode.UNAUTHORIZED);
    }
}
