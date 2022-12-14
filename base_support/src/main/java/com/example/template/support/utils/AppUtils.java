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
 * App?????????
 */
public class AppUtils {

    /**
     * ?????????????????????
     *
     * @return ???????????????
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * ????????????handler
     *
     * @return ??????handler
     */
    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    /**
     * ???????????????id
     *
     * @return ?????????id
     */
    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * ????????????ContentResolver
     *
     * @return ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    /**
     * ??????????????????
     *
     * @return ????????????
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * ????????????Asset??????
     *
     * @return ??????Asset??????
     */
    public static AssetManager getAssetManager() {
        return getContext().getAssets();
    }

    /**
     * ???????????????
     *
     * @return ?????????
     */
    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /**
     * ??????????????????
     *
     * @param name  ?????????
     * @param clazz ?????????
     * @param <T>
     * @return ????????????
     */
    public static <T> T getSystemService(String name, Class<T> clazz) {
        return getSystemService(getContext(), name, clazz);
    }

    /**
     * ??????????????????
     *
     * @param context ?????????
     * @param name    ?????????
     * @param clazz   ?????????
     * @param <T>
     * @return ????????????
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
     * ????????????????????????
     *
     * @return ??????????????????
     */
    public static ActivityLifecycleHelper getActivityLifecycleHelper() {
        return BaseApplication.sLifecycleHelper;
    }

    /**
     * ??????????????????
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
     * ???????????????
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
     * ???????????????
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * ???????????????
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param event    ????????????
     * @param view     ????????????view
     * @param activity ????????????activity
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param event    ????????????
     * @param view     ????????????view
     * @param activity ????????????activity
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
     * ??????SD?????????
     *
     * @return ??????sd?????????????????????null
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED);   //??????sd???????????????
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//???????????????
        }
        return sdDir;
    }

    /**
     * ????????????
     *
     * @param data
     */
    public static void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK ????????????????????????????????????????????? app
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
     * ??????????????????????????????
     *
     * @return true?????????????????????????????????
     * fasle???????????????????????????????????????
     */
    public static boolean isRunOnUIThread() {
        // ??????????????????id, ??????????????????id????????????id??????, ???????????????????????????
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param r ?????????Runnable??????
     */
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // ??????????????????, ????????????
            r.run();
        } else {
            // ??????????????????, ??????handler????????????????????????
            getHandler().post(r);
        }
    }

    /**
     * ??????
     */
    public static void shutDown() {
        try {
            //??????ServiceManager???
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            //??????ServiceManager???getService??????
            Method getService = ServiceManager.getMethod("getService", String.class);
            //??????getService??????RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            //??????IPowerManager.Stub???
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            //??????asInterface??????
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //??????asInterface????????????IPowerManager??????
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //??????shutdown()??????
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class,
                    boolean.class);
            //??????shutdown()??????
            shutdown.invoke(oIPowerManager, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????? App
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
     * ??????app
     */
    public static void rebootApp() {
        rebootApp(1000);
    }

    /**
     * ??????app
     *
     * @param delayMillis ????????????
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static void rebootApp(int delayMillis) {
        Intent intent = IntentUtils.getLaunchAppIntent(BaseApplication.getContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = XUtil.getSystemService(Context.ALARM_SERVICE, AlarmManager.class);
        if (mgr != null) {
            // 1?????????????????????
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delayMillis, restartIntent);
        }
        //????????????
        exitApp();
    }

    /**
     * ?????? App ??????
     *
     * @return App ??????
     */
    public static String getAppPackageName() {
        return BaseApplication.getContext().getPackageName();
    }

    /**
     * ?????? App ????????????
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ????????????
     *
     * @param packageName ??????
     */
    public static void getAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName, true));
    }

    /**
     * ?????? App ??????
     *
     * @return App ??????
     */
    public static String getAppName() {
        return getAppName(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ??????
     *
     * @param packageName ??????
     * @return App ??????
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
     * ?????? App ??????
     *
     * @return App ??????
     */
    public static Drawable getAppIcon() {
        return getAppIcon(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ??????
     *
     * @param packageName ??????
     * @return App ??????
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
     * ?????? App ??????
     *
     * @return App ??????
     */
    public static String getAppPath() {
        return getAppPath(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ??????
     *
     * @param packageName ??????
     * @return App ??????
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
     * ?????? App ?????????
     *
     * @return App ?????????
     */
    public static String getAppVersionName() {
        return getAppVersionName(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ?????????
     *
     * @param packageName ??????
     * @return App ?????????
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
     * ?????? App ?????????
     *
     * @return App ?????????
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ?????????
     *
     * @param packageName ??????
     * @return App ?????????
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
     * ?????? App ?????????????????????
     *
     * @return {@code true}: ???<br>{@code false}: ???
     */
    public static boolean isSystemApp() {
        return isSystemApp(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ?????????????????????
     *
     * @param packageName ??????
     * @return {@code true}: ???<br>{@code false}: ???
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
     * ?????? App ????????? Debug ??????
     *
     * @return {@code true}: ???<br>{@code false}: ???
     */
    public static boolean isAppDebug() {
        return isAppDebug(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ????????? Debug ??????
     *
     * @param packageName ??????
     * @return {@code true}: ???<br>{@code false}: ???
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
     * ?????? App ??????
     *
     * @return App ??????
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ??????
     *
     * @param packageName ??????
     * @return App ??????
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
     * ???????????????????????? SHA1 ???
     * <p>???????????????????????????????????? key ????????????</p>
     *
     * @return ??????????????? SHA1 ?????????, ?????????53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(BaseApplication.getContext().getPackageName());
    }

    /**
     * ???????????????????????? SHA1 ???
     * <p>???????????????????????????????????? key ????????????</p>
     *
     * @param packageName ??????
     * @return ??????????????? SHA1 ?????????, ?????????53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
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
     * ?????? App ??????????????????
     *
     * @return {@code true}: ???<br>{@code false}: ???
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
     * ?????? App ??????????????????
     * <p>????????????????????? App?????? SDK ?????? 21 ??????
     * ??????????????? {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName ??????
     * @return {@code true}: ???<br>{@code false}: ???
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(PACKAGE_USAGE_STATS)
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * ?????????TopActivity
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
     * ??????TopActivity?????????
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
     * ?????? App ????????? Bean ???
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
         * @param name        ??????
         * @param icon        ??????
         * @param packageName ??????
         * @param packagePath ?????????
         * @param versionName ?????????
         * @param versionCode ?????????
         * @param isSystem    ??????????????????
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
     * ?????? App ??????
     * <p>AppInfo???????????????????????????????????????????????? Code????????????????????????</p>
     *
     * @return ??????????????? AppInfo
     */
    public static com.xuexiang.xutil.app.AppUtils.AppInfo getAppInfo() {
        return getAppInfo(BaseApplication.getContext().getPackageName());
    }

    /**
     * ?????? App ??????
     * <p>AppInfo???????????????????????????????????????????????? Code????????????????????????</p>
     *
     * @param packageName ??????
     * @return ??????????????? AppInfo
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
     * ?????? AppInfo ??? Bean
     *
     * @param pm ????????????
     * @param pi ????????????
     * @return AppInfo ???
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
     * ????????????????????? App ??????
     * <p>{@link #getBean(PackageManager, PackageInfo)}
     * ???????????????????????????????????????????????????????????? Code????????????????????????</p>
     * <p>??????????????? getBean ??????</p>
     *
     * @return ?????????????????? AppInfo ??????
     */
    public static List<com.xuexiang.xutil.app.AppUtils.AppInfo> getAppsInfo() {
        List<com.xuexiang.xutil.app.AppUtils.AppInfo> list = new ArrayList<>();
        PackageManager pm = getPackageManager();
        // ??????????????????????????????????????????
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
     * ??????manifest????????????meta-data?????????
     *
     * @return meta-data?????????
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
     * ??????meta-data??????String????????????
     *
     * @param key
     * @return String????????????
     */
    @Nullable
    public static String getStringValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getString(key) : null;
    }

    /**
     * ??????meta-data??????Int????????????
     *
     * @param key
     * @return Int????????????
     */
    public static int getIntValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getInt(key) : 0;
    }

    /**
     * ??????meta-data??????Float????????????
     *
     * @param key
     * @return Float????????????
     */
    public static float getFloatValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getFloat(key) : 0F;
    }

    /**
     * ??????meta-data??????Double????????????
     *
     * @param key
     * @return Double????????????
     */
    public static double getDoubleValueInMetaData(String key) {
        Bundle metaData = com.xuexiang.xutil.app.AppUtils.getMetaDatas();
        return metaData != null ? metaData.getDouble(key) : 0D;
    }

    /**
     * ?????? App ????????????
     *
     * @param dirPaths ????????????
     * @return {@code true}: ??????<br>{@code false}: ??????
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
     * ?????? App ????????????
     *
     * @param dirs ??????
     * @return {@code true}: ??????<br>{@code false}: ??????
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
     * ?????? App ????????????
     *
     * @param action   action
     * @param category category
     * @return {@code true}: ?????????<br>{@code false}: ?????????
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * ?????? App ????????????
     *
     * @param packageName ??????
     * @return {@code true}: ?????????<br>{@code false}: ?????????
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * ?????? App(?????? 8.0)
     * <p>8.0 ???????????????
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  ????????????
     * @param authority 7.0 ?????????????????????????????????????????????{@code <provider>}??? authorities ??????
     *                  <br>?????? https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final String filePath, final String authority) {
        installApp(com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * ?????? App????????? 8.0???
     * <p>8.0 ???????????????
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      ??????
     * @param authority 7.0 ?????????????????????????????????????????????{@code <provider>}??? authorities ??????
     *                  <br>?????? https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final File file, final String authority) {
        if (com.xuexiang.xutil.file.FileUtils.isFileExists(file)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getInstallAppIntent(file, authority, true));
    }

    /**
     * ?????? App????????? 8.0???
     * <p>8.0 ???????????????
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param filePath    ????????????
     * @param authority   7.0 ?????????????????????????????????????????????{@code <provider>}??? authorities ??????
     *                    <br>?????? https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode ?????????
     */
    @RequiresPermission(REQUEST_INSTALL_PACKAGES)
    public static void installApp(final Activity activity,
                                  final String filePath,
                                  final String authority,
                                  final int requestCode) {
        installApp(activity, com.xuexiang.xutil.file.FileUtils.getFileByPath(filePath), authority, requestCode);
    }

    /**
     * ?????? App????????? 8.0???
     * <p>8.0 ???????????????
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    activity
     * @param file        ??????
     * @param authority   7.0 ?????????????????????????????????????????????{@code <provider>}??? authorities ??????
     *                    <br>?????? https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param requestCode ?????????
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
     * ???????????? App
     * <p>??? root ???????????????
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath ????????????
     * @return {@code true}: ????????????<br>{@code false}: ????????????
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
     * ???????????? App ???Android7.0???????????????
     * <p>??? root ???????????????
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath ????????????
     * @return {@code true}: ????????????<br>{@code false}: ????????????
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
     * ???????????? App ???Android7.0??????????????????
     * <p>??? root ???????????????
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath ????????????
     * @return {@code true}: ????????????<br>{@code false}: ????????????
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
     * ?????? App
     *
     * @param packageName ??????
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
    }

    /**
     * ?????? App
     *
     * @param activity    activity
     * @param packageName ??????
     * @param requestCode ?????????
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
     * ???????????? App
     * <p>??? root ???????????????
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName ??????
     * @param isKeepData  ??????????????????
     * @return {@code true}: ????????????<br>{@code false}: ????????????
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
     * ?????? App ????????? root ??????
     *
     * @return {@code true}: ???<br>{@code false}: ???
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
     * ?????? App
     *
     * @param packageName ??????
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        BaseApplication.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
    }

    /**
     * ?????? App
     *
     * @param activity    activity
     * @param packageName ??????
     * @param requestCode ?????????
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
