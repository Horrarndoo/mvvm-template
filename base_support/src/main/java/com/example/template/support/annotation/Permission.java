package com.example.template.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Horrarndoo on 2022/9/3.
 * <p>
 * 申请系统权限注解
 * <p>
 * （注解的方式需要单独创建一个PermissionActivity，这种方式体验不太友好，因此还是采用RxPermission实现权限动态申请）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Permission {
    /**
     * @return 需要申请权限的集合
     */
    String[] value();
}
