package com.kingja.seckill.util;

import java.util.regex.Pattern;

public class ValidatorUtil {

    public static boolean isMobile(String mobile) {
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$", mobile)) {
            return false;
        }
        return true;
    }

}
