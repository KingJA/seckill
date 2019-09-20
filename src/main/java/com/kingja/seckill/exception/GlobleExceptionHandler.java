package com.kingja.seckill.exception;

import com.kingja.seckill.result.CodeMsg;
import com.kingja.seckill.result.Result;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:TODO
 * Create Time:2019/9/6 0006 上午 10:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BindException) {
            BindException exception= (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            String defaultMessage = allErrors.get(0).getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(defaultMessage));
        }else{
          return   Result.error(CodeMsg.BIND_ERROR);
        }
    }
    @ExceptionHandler(value = ResultException.class)
    public Result resultExceptionHandler(ResultException e) {
        return Result.error(e.getCodeMsg());
    }
}
