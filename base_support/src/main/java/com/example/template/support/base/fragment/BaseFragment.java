package com.example.template.support.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.template.support.base.activity.BaseActivity;
import com.example.template.support.base.global.BaseApplication;
import com.example.template.support.utils.AppUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Horrarndoo on 2018/10/25.
 * <p>
 * fragment基类
 */
public abstract class BaseFragment extends SupportFragment {
    protected Context mContext;
    protected BaseActivity mActivity;
    protected BaseApplication mApplication;
    protected View mCreateView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            //            return inflater.inflate(getLayoutId(), null);
            mCreateView = inflater.inflate(getLayoutId(), container, false);
            return mCreateView;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
            initExternalArguments(getArguments());

        initData();
        initUI(view, savedInstanceState);
        ViewGroup rootView = (ViewGroup) mActivity.findViewById(android.R.id.content).getRootView();
        traversalView(rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    /**
     * 初始化外部传接进来的Bundle参数
     * <p>
     * 子类实现这个方法获取传进来的Bundle数据，如果子fragment没有外部传参，子类这个方法空实现
     *
     * @param args 外部传接进来的Bundle参数
     */
    protected abstract void initExternalArguments(@NonNull Bundle args);

    /**
     * 初始化UI
     */
    protected abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {
        mContext = AppUtils.getContext();
        mApplication = (BaseApplication) mActivity.getApplication();
    }

    /**
     * 处理回退事件
     *
     * @return true 事件已消费
     * <p>
     * false 事件向上传递
     */
    @Override
    public boolean onBackPressedSupport() {
        if ((getFragmentManager() != null ? getFragmentManager().getBackStackEntryCount() : 0) >
                1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //已经退栈到root fragment，交由Activity处理回退事件
            return false;
        }
        return true;
    }

    /**
     * 指定Fragment出栈
     *
     * @param fragment 指定fragment
     */
    protected void popFragment(Fragment fragment) {
        try {
            FragmentManager fm = fragment.getFragmentManager();
            if (fm != null) {
                fm.popBackStack();
                fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(fragment)
                        .commitAllowingStateLoss();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 判断App是否已存在
     *
     * @param name app包名
     * @return App是否已存在
     */
    protected boolean isAppExists(String name) {
        try {
            mActivity.getPackageManager().getApplicationInfo(name, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
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
     * 跳转至新的Fragment
     *
     * @param targetPath 要跳转的FragmentPath
     */
    public void startFragment(String targetPath) {
        startFragment(targetPath, null);
    }

    /**
     * 跳转至新的Fragment
     *
     * @param targetPath 要跳转的FragmentPath
     * @param bundle     携带的bundle数据
     */
    public void startFragment(String targetPath, Bundle bundle) {
        startFragment(targetPath, bundle, null);
    }

    /**
     * 跳转至新的Fragment
     *
     * @param targetPath 要跳转的FragmentPa
     * @param bundle     携带的bundle数据
     * @param callback   跳转回调
     */
    public void startFragment(String targetPath, Bundle bundle, NavigationCallback callback) {
        SupportFragment supportFragment = (SupportFragment) ARouter.getInstance().build
                (targetPath).with(bundle).navigation(null, callback);
        supportFragment.setArguments(bundle);
        startFragment(supportFragment);
    }

    /**
     * 跳转至新的Fragment，并且自身出栈
     *
     * @param targetPath 要跳转的FragmentPath
     */
    public void startFragmentWithPop(String targetPath) {
        startFragmentWithPop(targetPath, null);
    }

    /**
     * 跳转至新的Fragment，并且自身出栈
     *
     * @param targetPath 要跳转的FragmentPath
     * @param bundle     携带的bundle数据
     */
    public void startFragmentWithPop(String targetPath, Bundle bundle) {
        startFragmentWithPop(targetPath, bundle, null);
    }

    /**
     * 跳转至新的Fragment
     *
     * @param targetPath 要跳转的FragmentPa
     * @param bundle     携带的bundle数据
     * @param callback   跳转回调
     */
    public void startFragmentWithPop(String targetPath, Bundle bundle, NavigationCallback
            callback) {
        SupportFragment supportFragment = (SupportFragment) ARouter.getInstance().build
                (targetPath).with(bundle).navigation(null, callback);
        supportFragment.setArguments(bundle);
        startFragmentWithPop(supportFragment);
    }

    /**
     * 跳转至新的Fragment
     *
     * @param supportFragment 要跳转的Fragment(继承自SupportFragment)
     */
    public void startFragment(@NonNull SupportFragment supportFragment) {
        start(supportFragment);
    }

    /**
     * 跳转至新的Fragment，并且自身出栈
     *
     * @param supportFragment 要跳转的Fragment(继承自SupportFragment)
     */
    public void startFragmentWithPop(@NonNull SupportFragment supportFragment) {
        startWithPop(supportFragment);
    }

    /**
     * 跳转至新的Fragment，且跳转的Fragment pop出栈时会返回result，类似startActivityForResult
     *
     * @param supportFragment 要跳转的Fragment(继承自SupportFragment)
     * @param requestCode     requestCode参数
     */
    public void startFragmentForResult(@NonNull SupportFragment supportFragment, int
            requestCode) {
        startForResult(supportFragment, requestCode);
    }

    /**
     * 出栈至新目标Fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    public void popToFragment(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        popTo(targetFragmentClass, includeTargetFragment);
    }

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
    public void startActivity(String targetPath, Bundle bundle, NavigationCallback
            callback) {
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
        mActivity.startActivityForResult(targetPath, bundle, requestCode, callback);
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        mActivity.startActivity(clz);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        mActivity.startActivity(clz, intent);
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        mActivity.startActivity(clz, bundle);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        mActivity.startActivityForResult(clz, bundle, requestCode);
    }
}
