package com.example.template.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Horrarndoo on 2022/9/2.
 * 在需要切换主线程的地方添加@WorkInMainThread注解
 * <p>
 * 切换到主线程工作
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface MainThread {
    int delay() default 0;
}
