package com.imooc.common;

import javax.servlet.http.HttpSession;

/**
 * 会话工具类
 * <p>
 * 统一登录态的读写，避免各 Controller 直接用魔法 key 操作 session，
 * 同时强制判空（原项目未登录时 user.toString() 必抛 NPE）。
 */
public final class SessionUtil {

    /** 前台登录用户 */
    public static final String USER_KEY = "userName";
    /** 后台管理员 */
    public static final String ADMIN_KEY = "adminUserName";
    /** 验证码 */
    public static final String KAPTCHA_KEY = "verifyCode";

    private SessionUtil() {
    }

    public static String getUserName(HttpSession session) {
        return get(session, USER_KEY);
    }

    public static String getAdminName(HttpSession session) {
        return get(session, ADMIN_KEY);
    }

    /**
     * 获取当前登录用户，未登录则抛出业务异常（由全局异常处理器转成 401）
     */
    public static String requireUserName(HttpSession session) {
        String userName = getUserName(session);
        if (userName == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return userName;
    }

    /**
     * 获取当前管理员，未登录则抛出业务异常
     */
    public static String requireAdmin(HttpSession session) {
        String admin = getAdminName(session);
        if (admin == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return admin;
    }

    public static void setUserName(HttpSession session, String userName) {
        session.setAttribute(USER_KEY, userName);
    }

    public static void setAdminName(HttpSession session, String adminName) {
        session.setAttribute(ADMIN_KEY, adminName);
    }

    /**
     * 校验验证码：一次性消费，校验后立即失效
     */
    public static boolean verifyKaptcha(HttpSession session, String input) {
        Object kaptcha = session.getAttribute(KAPTCHA_KEY);
        // 无论成功与否都立即失效，防止复用暴破
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

    private static String get(HttpSession session, String key) {
        if (session == null) {
            return null;
        }
        Object value = session.getAttribute(key);
        return value == null ? null : value.toString();
    }
}
