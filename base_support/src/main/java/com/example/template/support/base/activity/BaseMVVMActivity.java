package com.example.template.support.base.activity;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.example.template.support.base.model.PageNavigationData;
import com.example.template.support.base.viewmodel.BaseViewModel;
import com.example.template.support.adapter.NavigationCallbackAdapter;
import com.example.template.support.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Horrarndoo on 2018/11/1.
 * <p>
 * 基于MVVM的Activity基类
 */
public abstract class BaseMVVMActivity<T1 extends ViewDataBinding, T2 extends BaseViewModel>
        extends BaseActivity {
    protected T1 mBinding;
    protected T2 mViewModel;

    @Override
    protected void init(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(getViewModelClass());
        setBindingData();
        super.init(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        //可以在ViewModel中统一处理页面跳转
        mViewModel.getPageNavigationData().observe(this, new Observer<PageNavigationData>() {
            @Override
            public void onChanged(@Nullable PageNavigationData pageNavigationData) {
                if (pageNavigationData == null)
                    return;

                switch (pageNavigationData.type) {
                    case PageNavigationData.TARGET_TYPE_ACTIVITY_CLASS:
                        startActivity(pageNavigationData.targetClass, pageNavigationData.bundle);
                        break;

                    case PageNavigationData.TARGET_TYPE_ACTIVITY_URL:
                        startActivity(pageNavigationData.targetPath, pageNavigationData.bundle, new
                                NavigationCallbackAdapter() {
                                    @Override
                                    public void onLost(Postcard postcard) {
                                        LogUtils.e("url path error, postcard = " +
                                                postcard.toString());
                                    }
                                });
                        break;

                    case PageNavigationData.TARGET_TYPE_FRAGMENT_CLASS:
                        LogUtils.e("Page navigation type error.");
                        break;

                    case PageNavigationData.TARGET_TYPE_FRAGMENT_URL:
                        LogUtils.e("Page navigation type error.");
                        break;

                    case PageNavigationData.TARGET_TYPE_ACTIVITY_EXTERNAL:
                        //跳转外部Activity，通过intent跳转
                        startActivity(pageNavigationData.intent);
                        break;

                    default:
                        LogUtils.e("Page navigation type error.");
                        break;
                }
            }
        });
    }

    /**
     * 获取当前View绑定的ViewModel.class
     * <p>
     * 交由子类实现
     *
     * @return 当前View绑定的ViewModel.class
     */
    @NonNull
    protected abstract Class<T2> getViewModelClass();

    /**
     * 设置DataBinding绑定的xml数据对象
     */
    protected abstract void setBindingData();
}
