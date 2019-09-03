package com.kingja.seckill.result;

/**
 * Description:TODO
 * Create Time:2018/8/6 17:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result success(Object data) {
        return new Result(0, "操作成功", data);
    }

}
