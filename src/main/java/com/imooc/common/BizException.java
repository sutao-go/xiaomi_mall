package com.imooc.common;

/**
 * 业务异常：由全局异常处理器捕获并转换成 {@link Result}
 */
public class BizException extends RuntimeException {

    private final int code;

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BizException(ResultCode resultCode, String msg) {
        super(msg);
        this.code = resultCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
