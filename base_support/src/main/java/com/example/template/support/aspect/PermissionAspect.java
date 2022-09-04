package com.example.template.support.aspect;

import com.example.template.support.annotation.Permission;
import com.example.template.support.manager.PermissonManager;
import com.example.template.support.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * Created by Horrarndoo on 2022/9/3.
 * <p>
 * 申请系统权限切片，根据注解值申请所需运行权限
 */
@Aspect
public class PermissionAspect {
    @Pointcut("within(@com.example.template.support.annotation.Permission *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.example.template.support.annotation.Permission * *(..)) || " +
            "methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @Around("method() && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        PermissionUtils.permission(permission.value())
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        try {
                            joinPoint.proceed();//获得权限，执行原方法
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (PermissonManager.getInstance().getOnPermissionDeniedListener() != null) {
                            PermissonManager.getInstance().getOnPermissionDeniedListener().onDenied(permissionsDenied);
                        }
                    }
                })
                .request();
    }
}
