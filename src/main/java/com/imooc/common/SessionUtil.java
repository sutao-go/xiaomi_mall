package com.imooc.common;

import cn.dev33.satoken.stp.StpUtil;

import jakarta.servlet.http.HttpSession;

/**
 * 会话 / 登录态工具类
 * <p>
 * 说明：项目的“登录身份”已交由 Sa-Token 维护（{@link StpUtil} 前台、{@link StpAdminUtil} 后台），
 * 这里只保留两件事：
 * 1. 验证码的读写（验证码天然适合放 session，且需要一次性消费）
 * 2. 兼容存量代码：部分未重构的后台接口仍从 session 读取 userName
 * <p>
 * requireUserName / requireAdmin 保留原方法签名，内部改为查询 Sa-Token，
 * 这样上层 Controller 无需改动即可完成切换。
 */
public final class SessionUtil {

    /** 前台登录用户（存量兼容用，新代码请用 StpUtil） */
    public static final String USER_KEY = "userName";
    /** 后台管理员（存量兼容用，新代码请用 StpAdminUtil） */
    public static final String ADMIN_KEY = "adminUserName";
    /** 验证码 */
    public static final String KAPTCHA_KEY = "verifyCode";

    private SessionUtil() {
    }

    /**
     * 获取当前登录用户（走 Sa-Token），未登录抛 401
     * @param session 仅为兼容保留，实际不再使用
     */
    public static String requireUserName(HttpSession session) {
        if (!StpUtil.isLogin()) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return StpUtil.getLoginIdAsString();
    }

    /**
     * 获取当前管理员（走 Sa-Token 后台账号体系），未登录抛 401
     * @param session 仅为兼容保留，实际不再使用
     */
    public static String requireAdmin(HttpSession session) {
        if (!StpAdminUtil.isLogin()) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return StpAdminUtil.getLoginIdAsString();
    }

    public static void setUserName(HttpSession session, String userName) {
        session.setAttribute(USER_KEY, userName);
    }

    public static void setAdminName(HttpSession session, String adminName) {
        session.setAttribute(ADMIN_KEY, adminName);
    }

    /**
     * 校验验证码：一次性消费，校验后立即失效（防止复用暴破）
     */
    public static boolean verifyKaptcha(HttpSession session, String input) {
        Object kaptcha = session.getAttribute(KAPTCHA_KEY);
        session.removeAttribute(KAPTCHA_KEY);
        return kaptcha != null && input != null && kaptcha.toString().equalsIgnoreCase(input.trim());
    }

    public static void setKaptcha(HttpSession session, String kaptcha) {
        session.setAttribute(KAPTCHA_KEY, kaptcha);
    }

    public static void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}
