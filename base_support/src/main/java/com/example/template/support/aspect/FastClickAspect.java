package com.example.template.support.aspect;

import android.view.View;

import com.example.template.support.annotation.FastClickLimit;
import com.example.template.support.utils.FastClickCheckUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by Horrarndoo on 2022/8/31.
 * <p>
 * 快速点击切面类
 */
@Aspect
public class FastClickAspect {
    //默认限制1s
    public static final long FAST_CLICK_INTERVAL_GLOBAL = 1000L;

    @Pointcut("execution(@com.example.template.support.annotation.FastClickLimit * *(..))")
    public void clickMethod() {
    }

    //    @Pointcut("execution(void com.mjt..lambda*(android.view.View))")
    //    public void viewOnClickWithLambda() {
    //    }
    //
    //    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    //    public void viewOnClick() {
    //    }

    @Around("clickMethod()")
    //    @Around("clickMethod() || viewOnClick() || viewOnClickWithLambda()")
    public void aroundViewOnClick(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出JoinPoint的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 取出JoinPoint的方法
        Method method = methodSignature.getMethod();

        // 1. 全局统一的默认时间间隔
        long interval = FAST_CLICK_INTERVAL_GLOBAL;

        if (method.isAnnotationPresent(FastClickLimit.class)) {
            // 2. 如果方法使用了@FastClick修饰，取出定制的时间间隔
            FastClickLimit fastClick = method.getAnnotation(FastClickLimit.class);
            if (fastClick != null) {
                interval = fastClick.interval();
            }
        }
        // 取出目标对象
        View target = (View) joinPoint.getArgs()[0];
        // 3. 根据点击间隔是否超过interval，判断是否为快速点击
        if (!FastClickCheckUtil.isFastClick(target, interval)) {
            joinPoint.proceed();
        }
    }
}
