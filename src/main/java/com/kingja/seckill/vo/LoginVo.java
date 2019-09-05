package com.kingja.seckill.vo;

/**
 * Description:TODO
 * Create Time:2019/9/5 0005 下午 5:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginVo {
    private String mobile;
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
