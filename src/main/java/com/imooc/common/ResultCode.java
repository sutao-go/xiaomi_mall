package com.imooc.common;

/**
 * 统一状态码
 * <p>
 * 替代原项目散落各处的魔法值（"200"/"202"/"203"/"204"/"303"/"404"/"405"/"100" 等）。
 */
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未登录或登录已失效"),
    FORBIDDEN(403, "无权访问该资源"),
    NOT_FOUND(404, "资源不存在"),
    CONFLICT(409, "数据冲突"),
    KAPTCHA_ERROR(412, "验证码错误或已失效"),
    ACCOUNT_DISABLED(423, "账号已被封禁"),
    ERROR(500, "服务器开小差了，请稍后重试");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
