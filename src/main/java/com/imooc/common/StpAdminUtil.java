package com.imooc.common;

import cn.dev33.satoken.stp.StpLogic;

/**
 * 后台管理员的独立账号体系（Sa-Token 多账号）
 * <p>
 * 用途：与前台用户（StpUtil，loginType=login）完全隔离。
 * 原实现里后台登录把管理员写进前台 userName，导致任意前台登录用户
 * 都能通过后台鉴权；这里用独立的 loginType 彻底隔开。
 */
public class StpAdminUtil {

    /** 账号体系标识，与前台默认的 "login" 区分 */
    public static final String TYPE = "admin";

    private static final StpLogic STP_LOGIC = new StpLogic(TYPE);

    private StpAdminUtil() {
    }

    public static StpLogic stpLogic() {
        return STP_LOGIC;
    }

    public static void login(Object id) {
        STP_LOGIC.login(id);
    }

    public static void logout() {
        STP_LOGIC.logout();
    }

    public static boolean isLogin() {
        return STP_LOGIC.isLogin();
    }

    public static String getLoginIdAsString() {
        return STP_LOGIC.getLoginIdAsString();
    }
}
