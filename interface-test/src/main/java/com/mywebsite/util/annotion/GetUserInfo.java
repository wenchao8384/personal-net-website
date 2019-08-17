package com.mywebsite.util.annotion;

import java.lang.annotation.*;

/**
 * 获取用户信息自定义注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetUserInfo {
}
