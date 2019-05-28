package com.jee.common.result;

public enum CodeMsg {

    //通用的错误码
    UNKNOWN_ERROR(-1, "未知异常"),
    SUCCESS(0, "success"),
    BIND_ERROR(500101, "参数校验异常：%s"),
    SERVER_ERROR(500100, "服务端异常"),
    REQUEST_TYPE_ERROR(500102, "请求类型异常：%s"),
    PERMISSION_ERROR(500103, "权限不足"),
    SQL_DATA_ACCESS_ERROR(500111, "SQL数据验证失败"),
    COMMON_NOFOUND_HANDLER(500111, "路径不存在，请检查路径是否正确")


    ;


    public int code;
    public String msg;


    CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object...args){
        this.msg = String.format(this.msg, args);
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
