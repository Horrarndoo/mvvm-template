package com.example.template.support.annotation;

import com.example.template.support.aspect.FastClickAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Horrarndoo on 2022/8/31.
 * <p>
 * 在需要定制时间间隔的地方添加@FastClick注解
 * <p>
 * 限制按钮快速点击
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FastClickLimit {
    long interval() default FastClickAspect.FAST_CLICK_INTERVAL_GLOBAL;
}
