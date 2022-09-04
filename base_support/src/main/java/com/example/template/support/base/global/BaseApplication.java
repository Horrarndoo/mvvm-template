package com.example.template.support.base.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.template.base_support.BuildConfig;
import com.example.template.support.helper.ActivityLifecycleHelper;
import com.example.template.support.helper.MMKVHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xuexiang.xutil.XUtil;

import androidx.multidex.MultiDex;
import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by Horrarndoo on 2022/8/30.
 * <p>
 * Application基类
 */
public class BaseApplication extends Application {
    private static final String LOG_TAG = "EXAMPLE_LOG";
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static BaseApplication mApp;
    //activity生命周期管理
    public static ActivityLifecycleHelper sLifecycleHelper = new ActivityLifecycleHelper();

    public static synchronized BaseApplication getInstance() {
        return mApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();
        init();
    }

    private void init() {
        //注册Activity声明周期
        registerActivityLifecycleCallbacks(sLifecycleHelper);

        //logger
        initLogger();

        //XUtil
        XUtil.init(this);
        XUtil.debug(BuildConfig.DEBUG);

        // ANR监控
        ANRWatchDogHelper.init();

        //ARouter
        if (BuildConfig.IS_SHOW_LOG) {// These two lines must be written before init,
            // otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun
            // mode, you must turn on debug mode! Online version needs to be closed, otherwise
            // there is a security risk)
        }
        // As early as possible, it is recommended to initialize in the Application
        ARouter.init(this);

        //崩溃处理
        if (BuildConfig.IS_COLLECT_CRASH_INFO)
            CrashHandler.getInstance().init(this);

        //后续有换肤需求采用此方案
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())// 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater()) // material design
                .addInflater(new SkinConstraintViewInflater())// ConstraintLayout
                .addInflater(new SkinCardViewInflater())// CardView v7 控件换肤初始化[可选]
                //.setSkinStatusBarColorEnable(false)  //关闭状态栏换肤，默认打开[可选]
                //.setSkinWindowBackgroundEnable(false)//关闭windowBackground
                // 换肤，默认打开[可选]
                .loadSkin();

        //初始化mmkv
        MMKVHelper.getInstance().init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)     // （可选）是否显示线程信息。 默认值为true
                .methodCount(2)            //（可选）要显示的方法行数。 默认2
                .methodOffset(0)           //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                //.logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag(LOG_TAG) //（可选）TAG内容. 默认是 PRETTY_LOGGER
                .build();
        /*
         * 如果需要将日志存储到本地文件，则需要使用DiskLogAdapter,同时需要声明存储权限,Logger 默认生成csv文件,存储在
         * /storage/emulated/0/logger目录下
         * 如果针对不同的页面 Logger 的配置不同， 可以使用 Logger.clearLogAdapters(), 然后进行重新配置。
         * 控制打印开关,通过适配器控制是否打印日志：只要覆盖isLoggable()方法，返回false-不打印，返回true-打印
         * 临时修改TAG打印日志：Logger.t("临时TAG").d("");
         */
        //        Logger.addLogAdapter(new DiskLogAdapter());
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.IS_SHOW_LOG;
            }
        });
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    public void finish() {
        onDestroy();
    }

    public void onDestroy() {
    }
}
