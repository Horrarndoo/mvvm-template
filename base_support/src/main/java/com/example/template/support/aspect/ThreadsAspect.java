package com.example.template.support.aspect;

import android.annotation.SuppressLint;

import com.example.template.support.annotation.Background;
import com.example.template.support.annotation.MainThread;
import com.example.template.support.bean.MethodInfo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Horrarndoo on 2022/9/2.
 * <p>
 * 线程切面类（内部线程切换通过RxJava实现）
 */
@Aspect
public class ThreadsAspect {
    @SuppressLint("CheckResult")
    @Around("execution(@com.example.template.support.annotation.Background * *(..))")
    public void doBackground(ProceedingJoinPoint joinPoint) {
        Background background = getMethodAnnotation(joinPoint, Background.class);
        Observable.timer(background.delay(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Around("execution(@com.example.template.support.annotation.MainThread * *(..))")
    public void doMainThread(ProceedingJoinPoint joinPoint) {
        MainThread mainThread = getMethodAnnotation(joinPoint, MainThread.class);
        Observable.timer(mainThread.delay(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }

    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint,
                                                         Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }


    /**
     * 获取方法信息
     *
     * @param joinPoint
     */
    private MethodInfo getMethodInfo(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String threadName = Thread.currentThread().getName();
        return new MethodInfo(className, methodName, threadName);
    }
}
