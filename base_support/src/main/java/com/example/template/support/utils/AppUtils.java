package com.example.template.support.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.template.support.base.global.BaseApplication;
import com.example.template.support.helper.ActivityLifecycleHelper;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.ProcessUtils;
import com.xuexiang.xutil.app.ServiceUtils;
import com.xuexiang.xutil.common.ShellUtils;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.file.CleanUtils;
import com.xuexiang.xutil.file.FileUtils;
import com.xuexiang.xutil.security.EncryptUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import static android.Manifest.permission.INSTALL_PACKAGES;
import static android.Manifest.permission.KILL_BACKGROUND_PROCESSES;
import static android.Manifest.permission.PACKAGE_USAGE_STATS;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;

/**
 * Created by Horrarndoo on 2018/10/25.
 * <p>
 * App工具类
 */
public class AppUtils {

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 获取全局ContentResolver
     *
     * @return ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    /**
     * 获取全局资源
     *
     * @return 全局资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取全局Asset管理
     *
     * @return 全局Asset管理
     */
    public static AssetManager getAssetManager() {
        return getContext().getAssets();
    }

    /**
     * 获取包管理
     *
     * @return 包管理
     */
    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /**
     * 获取系统服务
     *
     * @param name  服务名
     * @param clazz 服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(String name, Class<T> clazz) {
        return getSystemService(getContext(), name, clazz);
    }

    /**
     * 获取系统服务
     *
     * @param context 上下文
     * @param name    服务名
     * @param clazz   服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(Context context, String name, Class<T> clazz) {
        if (!TextUtils.isEmpty(name) && clazz != null && context != null) {
            Object obj = context.getSystemService(name);
            return clazz.isInstance(obj) ? (T) obj : null;
        } else {
            return null;
        }
    }

    /**
     * 获取生命周期管理
     *
     * @return 生命周期管理
     */
    public static ActivityLifecycleHelper getActivityLifecycleHelper() {
        return BaseApplication.sLifecycleHelper;
    }

    /**
     * 获取版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInputClearFocus(View et) {
        if (et == null)
            return;

        if (!(et instanceof EditText))
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager == null)
            return;

        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
        et.clearFocus();
    }

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param event    焦点位置
     * @param view     焦点控件view
     * @param activity 控件所在activity
     */
    public static void hideKeyboardClearFocus(MotionEvent event, View view, Activity activity) {
        try {
            if (view == null)
                return;

            if (view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0];
                int top = location[1];
                int right = left + view.getWidth();
                int bottom = top + view.getHeight();
                if (event.getX() < left || event.getX() > right
                        || event.getY() < top || event.getY() > bottom) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager == null)
                        return;
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    view.clearFocus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param event    焦点位置
     * @param view     焦点控件view
     * @param activity 控件所在activity
     */
    public static void hideKeyboard(MotionEvent event, View view, Activity activity) {
        try {
            if (view == null)
                return;

            if (view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0];
                int top = location[1];
                int right = left + view.getWidth();
                int bottom = top + view.getHeight();
                if (event.getX() < left || event.getX() > right
                        || event.getY() < top || event.getY() > bottom) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager == null)
                        return;
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    /**
     * 安装文件
     *
     * @param data
     */
    public static void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

    public static void copy2clipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip", text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    /**
     * 关机
     */
    public static void shutDown() {
        try {
            //获得ServiceManager类
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", String.class);
            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            //获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class,
                    boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出 App
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static void exitApp() {
        if (getActivityLifecycleHelper() != null) {
            getActivityLifecycleHelper().exit();
        }

        ServiceUtils.stopAllRunningService(BaseApplication.getContext());
        ProcessUtils.killBackgroundProcesses(BaseApplication.getContext().getPackageName());
        BaseApplication.getInstance().finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 重启app
     */
    public static void rebootApp() {
        rebootApp(1000);
    }

    /**
     * 重启app
     *
     * @param delayMillis 延迟时间
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static void rebootApp(int delayMillis) {
        Intent intent = IntentUtils.getLaunchAppIntent(BaseApplication.getContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = XUtil.getSystemService(Context.ALARM_SERVICE, AlarmManager.class);
        if (mgr != null) {
            // 1秒钟后重启应用
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delayMillis, restartIntent);
        }
        //退出程序
        exitApp();
    }

    /**
     * 获取 App 包名
     *
     * @return App 包名
     */
    public static String getAppPackageName() {
        return BaseApplication.getContext().getPackageName();
    }

    /**
     * 获取 App 具体设置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 具体设置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName, true));
    }

    /**
     * 获取 App 名称
     *
     * @return App 名称
     */
    public static String getAppName() {
        return getAppName(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param packageName 包名
     * @return App 名称
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 图标
     *
     * @return App 图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 图标
     *
     * @param packageName 包名
     * @return App 图标
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 路径
     *
     * @return App 路径
     */
    public static String getAppPath() {
        return getAppPath(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param packageName 包名
     * @return App 路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本号
     *
     * @return App 版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param packageName 包名
     * @return App 版本号
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本码
     *
     * @return App 版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 版本码
     *
     * @param packageName 包名
     * @return App 版本码
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(BaseApplication.getContext().getPackageName());
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return isAppDebug(BaseApplication.getContext().getPackageName());
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 App 签名
     *
     * @return App 签名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 签名
     *
     * @param packageName 包名
     * @return App 签名
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的 SHA1 值
     * <p>可据此判断高德，百度地图 key 是否正确</p>
     *
     * @return 应用签名的 SHA1 字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取应用签名的的 SHA1 值
     * <p>可据此判断高德，百度地图 key 是否正确</p>
     *
     * @param packageName 包名
     * @return 应用签名的 SHA1 字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1(final String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null) {
            return null;
        }
        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 判断 App 是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = getActivityManager();
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(BaseApplication.getContext().getPackageName());
            }
        }
        return false;
    }

    /**
     * 判断 App 是否处于前台
     * <p>当不是查看当前 App，且 SDK 大于 21 时，
     * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(PACKAGE_USAGE_STATS)
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * 是否是TopActivity
     *
     * @param packageName
     * @return
     */
    public static boolean isTopActivity(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        ComponentName topActivity = getTopActivityComponent();
        return topActivity != null && packageName.equals(topActivity.getPackageName());
    }

    /**
     * 获取TopActivity的组件
     *
     * @return
     */
    public static ComponentName getTopActivityComponent() {
        ComponentName topActivity = null;
        ActivityManager activityManager = getActivityManager();
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager
                .getRunningTasks(1);
        if (runningTaskInfos != null) {
            topActivity = runningTaskInfos.get(0).topActivity;
        }
        return topActivity;
    }

    public static ActivityManager getActivityManager() {
        return (ActivityManager) BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 封装 App 信息的 Bean 类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem    是否系统应用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @NonNull
        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @return 当前应用的 AppInfo
     */
    public static com.xuexiang.xutil.app.AppUtils.AppInfo getAppInfo() {
        return getAppInfo(BaseApplication.getContext().getPackageName());
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的 AppInfo
     */
    public static com.xuexiang.xutil.app.AppUtils.AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 类
     */
    private static com.xuexiang.xutil.app.AppUtils.AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) {
            return null;
        }
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new com.xuexiang.xutil.app.AppUtils.AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 获取所有已安装 App 信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}
     * （名称，图标，包名，包路径，版本号，版本 Code，是否系统应用）</p>
     * <p>依赖上面的 getBean 方法</p>
     *
     * @return 所有已安装的 AppInfo 列表
     */
    public static List<com.xuexiang.xutil.app.AppUtils.AppInfo> getAppsInfo() {
        List<com.xuexiang.xutil.app.AppUtils.AppInfo> list = new ArrayList<>();
        PackageManager pm = getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            com.xuexiang.xutil.app.AppUtils.AppInfo ai = getBean(pm, pi);
            if (ai == null) {
                continue;
            }
            list.add(ai);
        }
        return list;
    }

    public static String getPackageName() {
        return BaseApplication.getContext().getPackageName();
    }

    /**
     * 获取manifest里注册的meta-data值集合
     *
     * @return meta-data值集合
     */
    @Nullable
    public static Bundle getMetaDatas() {
        try {
            PackageManager pm = getPackageManager();
            return pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return null;
    }

    /**
     * 获取meta-data中的String类型的值
     *
     * @param key
     * @return String类型的值
     */
    @Nullable
    public static String getStringValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getString(key) : null;
    }

    /**
     * 获取meta-data中的Int类型的值
     *
     * @param key
     * @return Int类型的值
     */
    public static int getIntValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getInt(key) : 0;
    }

    /**
     * 获取meta-data中的Float类型的值
     *
     * @param key
     * @return Float类型的值
     */
    public static float getFloatValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getFloat(key) : 0F;
    }

    /**
     * 获取meta-data中的Double类型的值
     *
     * @param key
     * @return Double类型的值
     */
    public static double getDoubleValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getDouble(key) : 0D;
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanInternalSp();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    private static final int APP_INSTALL_AUTO = 0;
    private static final int APP_INSTALL_INTERNAL = 1;
    private static final int APP_INSTALL_EXTERNAL = 2;

    /**
     * get params for pm install location
     *
     * @return
     */
    private static String getInstallLocationParams() {
        int location = getInstallLocation();
        switch (location) {
            case APP_INSTALL_INTERNAL:
                return "-f";
            case APP_INSTALL_EXTERNAL:
                return "-s";
            default:
                break;
        }
        return "";
    }

    /**
     * get system install location<br/>
     * can be set by System Menu Setting->Storage->Prefered install location
     *
     * @return
     */
    private static int getInstallLocation() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
        if (commandResult.result == 0 && commandResult.successMsg != null && commandResult.successMsg.length() > 0) {
            try {
                int location = Integer.parseInt(commandResult.successMsg.substring(0, 1));
                switch (location) {
                    case APP_INSTALL_INTERNAL:
                        return APP_INSTALL_INTERNAL;
                    case APP_INSTALL_EXTERNAL:
                        return APP_INSTALL_EXTERNAL;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Logger.e("pm get-install-location error");
            }
        }
        return APP_INSTALL_AUTO;
    }

    /**
     * whether context is system application
     *
     * @param context
     * @return
     */
    private static boolean isSystemApplication(Context context) {
        return context != null && isSystemApplication(context, context.getPackageName());

    }

    /**
     * whether packageName is system application
     *
     * @param context
     * @param packageName
     * @return
     */
    private static boolean isSystemApplication(Context context, String packageName) {
        return context != null && isSystemApplication(context.getPackageManager(), packageName);

    }

    /**
     * whether packageName is system application
     *
     * @param packageManager
     * @param packageName
     * @return <ul>
     * <li>if packageManager is null, return false</li>
     * <li>if package name is null or is empty, return false</li>
     * <li>if package name not exit, return false</li>
     * <li>if package name exit, but not system app, return false</li>
     * <li>else return true</li>
     * </ul>
     */
    private static boolean isSystemApplication(PackageManager packageManager, String packageName) {
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断 App 是否安装
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断 App 是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安装 App(支持 8.0)
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final String filePath, final String authority) {
        installApp(com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final File file, final String authority) {
        if (com.xuexiang.xutil.file.FileUtils.isFileExists(file)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getInstallAppIntent(file, authority, true));
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                    <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 请求值
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final Activity activity,
                                  final String filePath,
                                  final String authority,
                                  final int requestCode) {
        installApp(activity, com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安装 App（支持 8.0）
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param file        文件
     * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                    <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode 请求值
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final Activity activity,
                                  final File file,
                                  final String authority,
                                  final int requestCode) {
        if (com.xuexiang.xutil.file.FileUtils.isFileExists(file)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority),
                requestCode);
    }

    /**
     * 静默安装 App
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    @RequiresPermission(INSTALL_PACKAGES)
    public static boolean installAppSilent(final String filePath) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return installAppSilentBelow24(BaseApplication.getContext(), filePath);
        } else {
            return installAppSilentAbove24(BaseApplication.getContext().getPackageName(), filePath);
        }
    }

    /**
     * 静默安装 App 在Android7.0以下起作用
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    @RequiresPermission(INSTALL_PACKAGES)
    private static boolean installAppSilentBelow24(Context context, final String filePath) {
        File file = com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath);
        if (com.xuexiang.xutil.file.FileUtils.isFileExists(file)) {
            return false;
        }

        String pmParams = " -r " + getInstallLocationParams();

        StringBuilder command = new StringBuilder()
                .append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ")
                .append(pmParams).append(" ")
                .append(filePath.replace(" ", "\\ "));
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(
                command.toString(), !isSystemApplication(context), true);
        return commandResult.successMsg != null
                && (commandResult.successMsg.contains("Success") || commandResult.successMsg
                .contains("success"));
    }

    /**
     * 静默安装 App 在Android7.0及以上起作用
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    @RequiresPermission(INSTALL_PACKAGES)
    private static boolean installAppSilentAbove24(final String packageName, final String filePath) {
        File file = com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath);
        if (FileUtils.isFileExists(file)) {
            return false;
        }
        boolean isRoot = isDeviceRooted();
        String command = "pm install -i " + packageName + " --user 0 " + filePath;
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command, isRoot);
        return (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success"));
    }

    /**
     * 卸载 App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
    }

    /**
     * 卸载 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void uninstallApp(final Activity activity,
                                    final String packageName,
                                    final int requestCode) {
        if (isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 静默卸载 App
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName 包名
     * @param isKeepData  是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载失败
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        if (isSpace(packageName)) {
            return false;
        }
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall "
                + (isKeepData ? "-k " : "")
                + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command, isRoot, true);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib64 pm uninstall "
                    + (isKeepData ? "-k " : "")
                    + packageName;
            commandResult = ShellUtils.execCommand(command, isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }

    /**
     * 判断 App 是否有 root 权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCommand("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            Logger.dTag("AppUtils", "isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * 打开 App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
    }

    /**
     * 打开 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
    }
}
