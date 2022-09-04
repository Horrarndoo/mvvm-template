package com.example.template.app.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.template.app.BuildConfig;
import com.example.template.app.R;
import com.example.template.common.constants.ARouterPath;
import com.example.template.support.base.activity.BaseActivity;
import com.example.template.support.helper.RxHelper;
import com.example.template.support.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 启动页面
 */
@Route(path = ARouterPath.SplashActivity)
public class SplashActivity extends BaseActivity {
    //等待时间（单位：s）
    private static final int WAIT_TIME = BuildConfig.DEBUG ? 3 : 2;

    @Override
    protected void initData() {
        super.initData();
        requestPermissions();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SplashActivity.this);
        //请求权限全部结果
        rxPermission.request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
                        startCountDown();
                    }
                });
        //分别请求权限
        //        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_CALENDAR,
        //                Manifest.permission.READ_CALL_LOG,
        //                Manifest.permission.READ_CONTACTS,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.READ_SMS,
        //                Manifest.permission.RECORD_AUDIO,
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.CALL_PHONE,
        //                Manifest.permission.SEND_SMS);

        //        rxPermission.requestEach(
        //                        Manifest.permission.CAMERA,
        //                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                        Manifest.permission.READ_EXTERNAL_STORAGE,
        //                        Manifest.permission.ACCESS_FINE_LOCATION,
        //                        Manifest.permission.ACCESS_COARSE_LOCATION)
        //                .subscribe(new Consumer<Permission>() {
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        if (permission.granted) {
        //                            // 用户已经同意该权限
        //                            LogUtils.d(permission.name + " is granted.");
        //                        } else if (permission.shouldShowRequestPermissionRationale) {
        //                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        //                            LogUtils.d(permission.name + " is denied. More info should
        //                            be " +
        //                                    "provided.");
        //                        } else {
        //                            // 用户拒绝了该权限，并且选中『不再询问』
        //                            LogUtils.d(permission.name + " is denied.");
        //                        }
        //                    }
        //                });
    }

    /**
     * 开启计数器
     */
    private void startCountDown() {
        //        LogUtils.d("startCountDown");
        Observable.interval(1, TimeUnit.SECONDS)
                .take(WAIT_TIME)//计时次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //到这里的时候已经执行了一轮，因此要-1
                        return WAIT_TIME - aLong - 1;
                    }
                })
                .compose(RxHelper.<Long>rxSchedulerHelper())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        //                        LogUtils.d("value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        //                        LogUtils.d("onComplete");
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
