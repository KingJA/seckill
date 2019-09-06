package com.kingja.seckill.vo;

import com.kingja.seckill.validator.IsMobile;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Description:TODO
 * Create Time:2019/9/5 0005 下午 5:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return null == mobile ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return null == password ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
