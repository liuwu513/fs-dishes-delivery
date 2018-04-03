package com.fs.dishes.base.annotations;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * 
 * Created by liuwu on 2018/2/28 0028.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogManage {

	String value() default "";
}
