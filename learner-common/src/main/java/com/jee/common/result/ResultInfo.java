package com.jee.common.result;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
public class ResultInfo<T> {

    private int code;
    private String msg;
    private T data;
    private boolean success;

    /**
	 * sucess
	 */
    public static <T> ResultInfo<T> success(T data){
        return new ResultInfo<T>(data);
    }
    public static <T> ResultInfo<T> success(){
        return success(null);
    }
    /**
	 * error
	 */
    public static <T> ResultInfo<T> error(CodeMsg codeMsg){
        return new ResultInfo<T>(codeMsg);
    }

    private ResultInfo(T data) {
        this.code = CodeMsg.SUCCESS.code;
        this.msg = CodeMsg.SUCCESS.msg;
        this.data = data;
        this.success = true;
    }

    private ResultInfo(CodeMsg codeMsg) {
        if(codeMsg == null){
            return ;
        }
        this.code = codeMsg.code;
        this.msg = codeMsg.msg;
        this.success = false;
    }

    public ResultInfo(){

    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}

