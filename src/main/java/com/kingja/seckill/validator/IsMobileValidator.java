package com.kingja.seckill.validator;

import com.kingja.seckill.util.ValidatorUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description:TODO
 * Create Time:2019/9/6 0006 上午 9:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean require;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        require = constraintAnnotation.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (require) {
            return ValidatorUtil.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(value);
            }
        }

    }

}