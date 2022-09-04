package com.example.template.support.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.example.template.support.base.global.BaseApplication;
import com.example.template.support.helper.NavigationHelper;
import com.example.template.support.utils.AppUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static com.example.template.support.helper.NavigationHelper.ANIM_FINISH_IN;
import static com.example.template.support.helper.NavigationHelper.ANIM_FINISH_OUT;
import static com.example.template.support.helper.NavigationHelper.ANIM_START_IN;
import static com.example.template.support.helper.NavigationHelper.ANIM_START_OUT;

/**
 * Created by Horrarndoo on 2018/10/25.
 * <p>
 * activity基类
 */
public abstract class BaseActivity extends SupportActivity {
    protected BaseApplication mApplication;
    protected Context mContext;//全局上下文对象
    protected boolean isTransAnim;

    static {
        //5.0以下兼容vector
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeSetContentView();
        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        //fragment切换使用默认Vertical动画
        return new DefaultVerticalAnimator();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                View view = getCurrentFocus();
                AppUtils.hideKeyboard(ev, view, this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 执行setContentView之前的初始化（子类根据需求复写此方法）
     */
    protected void initBeforeSetContentView() {
    }

    /**
     * 初始化
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content).getRootView();
        traversalView(rootView);
    }

    /**
     * 遍历页面所有子控件（设置所有Edittext横屏下在当前界面弹出键盘）
     *
     * @param viewGroup 页面根布局
     */
    private void traversalView(ViewGroup viewGroup) {
        if (viewGroup == null)
            return;

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view);
            } else {
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    editText.setImeOptions(editText.getImeOptions() | EditorInfo
                            .IME_FLAG_NO_EXTRACT_UI);
                }
            }
        }
    }

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        mContext = AppUtils.getContext();
        mApplication = (BaseApplication) getApplication();
        setTransAnim(true);
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * [页面跳转]
     *
     * @param targetPath 要跳转的ActivityPath
     */
    public void startActivity(String targetPath) {
        startActivity(targetPath, null, null);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath 要跳转的ActivityPath
     * @param callback   跳转回调
     */
    public void startActivity(String targetPath, NavigationCallback callback) {
        startActivity(targetPath, null, callback);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath 要跳转的ActivityPath
     * @param bundle     携带的bundle数据
     */
    public void startActivity(String targetPath, Bundle bundle) {
        startActivity(targetPath, bundle, null);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath 要跳转的ActivityPath
     * @param bundle     携带的bundle数据
     * @param callback   跳转回调
     */
    public void startActivity(String targetPath, Bundle bundle, NavigationCallback callback) {
        startActivityForResult(targetPath, bundle, -1, callback);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath  要跳转的ActivityPath
     * @param requestCode startActivityForResult 的requestCode参数
     */
    public void startActivityForResult(String targetPath, int requestCode, NavigationCallback
            callback) {
        startActivityForResult(targetPath, null, requestCode, callback);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath  要跳转的ActivityPath
     * @param bundle      携带的bundle数据
     * @param requestCode startActivityForResult 的requestCode参数
     */
    public void startActivityForResult(String targetPath, Bundle bundle, int
            requestCode) {
        startActivityForResult(targetPath, bundle, requestCode, null);
    }

    /**
     * [页面跳转]
     *
     * @param targetPath  要跳转的ActivityPath
     * @param bundle      携带的bundle数据
     * @param requestCode startActivityForResult 的requestCode参数
     * @param callback    跳转回调
     */
    public void startActivityForResult(String targetPath, Bundle bundle, int requestCode,
                                       NavigationCallback callback) {
        NavigationHelper.navigation(this, targetPath, bundle, requestCode, callback);
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
        if (isTransAnim)
            overridePendingTransition(ANIM_START_IN, ANIM_START_OUT);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(ANIM_START_IN, ANIM_START_OUT);
    }

    /**
     * [页面跳转]
     *
     * @param intent intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(ANIM_START_IN, ANIM_START_OUT);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(ANIM_START_IN, ANIM_START_OUT);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        if (isTransAnim)
            overridePendingTransition(ANIM_START_IN, ANIM_START_OUT);
    }

    @Override
    public void finish() {
        super.finish();
        if (isTransAnim)
            overridePendingTransition(ANIM_FINISH_IN, ANIM_FINISH_OUT);
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 是否使用overridePendingTransition过度动画
     *
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        if (isTransAnim != NavigationHelper.isTransAnim())
            NavigationHelper.setIsTransAnim(isTransAnim);
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    protected void setTransAnim(boolean b) {
        isTransAnim = b;
        NavigationHelper.setIsTransAnim(b);
    }

    /**
     * 点亮屏幕
     */
    @SuppressLint("InvalidWakeLockTag")
    public void turnOnScreen() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager
                .SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        wakeLock.acquire(20 * 1000L);
        wakeLock.release();
    }

    /**
     * 重启设备
     */
    protected void reboot() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            pm.reboot("reboot.");
        }
    }
}
