package com.kingja.seckill.result;

/**
 * Description:TODO
 * Create Time:2018/8/11 19:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CodeMsg {
    private int code;
    private String msg;

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CodeMsg SUCCESS=new CodeMsg(0,"操作成功");
    public static CodeMsg ERROR_BIND=new CodeMsg(500101,"参数绑定异常:%s");
    public static CodeMsg ERROR_SERVER=new CodeMsg(500102,"服务器异常");
    public static CodeMsg ERROR_MOBILE_NOEXIST=new CodeMsg(500103,"手机号不存在");
    public static CodeMsg ERROR_PASSWORD=new CodeMsg(500104,"密码错误");
    /*5005xx秒杀*/
    public static CodeMsg MIAOSHA_OVER=new CodeMsg(500500,"秒杀活动已结束");
    public static CodeMsg NO_REPEAT_MIAOSHA=new CodeMsg(500501,"不能重复秒杀");


    public CodeMsg fillArgs(Object...args) {
        return new CodeMsg(this.code,String.format(this.msg,args));
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
}
