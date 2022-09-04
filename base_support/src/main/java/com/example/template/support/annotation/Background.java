package com.example.template.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Horrarndoo on 2022/9/2.
 * 在需要切换后台线程（子线程）的地方添加@Background注解
 * <p>
 * 切换到后台线程（子线程）工作
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface Background {
    int delay() default 0;
}
