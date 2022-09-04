package com.example.template.support.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.Postcard;
import com.example.template.support.adapter.NavigationCallbackAdapter;
import com.example.template.support.base.model.PageNavigationData;
import com.example.template.support.base.viewmodel.BaseViewModel;
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
 * 基于MVVM的Fragment基类
 */
public abstract class BaseMVVMFragment<T1 extends ViewDataBinding, T2 extends BaseViewModel> extends
        BaseFragment {

    protected T1 mBinding;
    protected T2 mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            //            return inflater.inflate(getLayoutId(), null);
            //            return inflater.inflate(getLayoutId(), container, false);
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mBinding.setLifecycleOwner(this);
            return mBinding.getRoot();
        }
    }

    @Override
    protected void initExternalArguments(@NonNull Bundle args) {
        //采用MVVM+Arouter，直接通过Arouter传参
    }

    @Override
    public void initData() {
        super.initData();
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(getViewModelClass());
        //绑定数据（vm、handler等）
        initBindingData();

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
                        try {
                            BaseFragment fragment = (BaseFragment) pageNavigationData.targetClass
                                    .newInstance();
                            if (pageNavigationData.bundle != null)
                                fragment.setArguments(pageNavigationData.bundle);
                            startFragment(fragment);
                        } catch (InstantiationException | IllegalAccessException | java.lang.InstantiationException e) {
                            e.printStackTrace();
                        }
                        break;

                    case PageNavigationData.TARGET_TYPE_FRAGMENT_URL:
                        startFragment(pageNavigationData.targetPath, pageNavigationData.bundle);
                        break;

                    case PageNavigationData.TARGET_TYPE_ACTIVITY_EXTERNAL:
                        //跳转外部Activity，通过intent跳转
                        startActivity(pageNavigationData.intent);
                        break;

                    default:
                        LogUtils.e("Page skip error.");
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
     * 初始化databinding绑定的xml数据对象（在initData后执行）
     */
    protected abstract void initBindingData();
}
