package com.kingja.seckill.exception;


import com.kingja.seckill.result.CodeMsg;

/**
 * Description:TODO
 * Create Time:2018/8/12 0:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResultException extends RuntimeException {
    private CodeMsg codeMsg;

    public ResultException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
