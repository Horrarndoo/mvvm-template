package com.example.template.support.base.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import com.example.template.support.base.activity.BaseActivity;
import com.example.template.support.base.fragment.BaseFragment;
import com.example.template.support.base.model.PageNavigationData;
import com.example.template.support.utils.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Horrarndoo on 2018/10/29.
 * <p>
 * viewModel基类
 */
public abstract class BaseViewModel extends ViewModel {
    private MutableLiveData<PageNavigationData> mPageNavigationData;

    public BaseViewModel() {
        init();
    }

    protected void init() {
        initFields();
    }

    protected boolean isEmpty(String s) {
        if (s != null)
            s = s.trim();
        return TextUtils.isEmpty(s);
    }

    protected void startFramemt(String targetPath) {
        this.startFramemt(targetPath, null);
    }

    protected void startFramemt(Class<? extends BaseFragment> targetFragmentClass) {
        this.startFramemt(targetFragmentClass, null);
    }

    protected void startFramemt(String targetPath, Bundle bundle) {
        PageNavigationData pkd = new PageNavigationData(PageNavigationData
                .TARGET_TYPE_FRAGMENT_URL, targetPath,
                bundle);
        mPageNavigationData.setValue(pkd);
    }

    protected void startFramemt(Class<? extends BaseFragment> targetFragmentClass, Bundle bundle) {
        PageNavigationData pkd = new PageNavigationData(PageNavigationData
                .TARGET_TYPE_FRAGMENT_CLASS,
                targetFragmentClass, bundle);
        mPageNavigationData.setValue(pkd);
    }

    protected void startActivity(String targetPath) {
        this.startActivity(targetPath, null);
    }

    protected void startActivity(Class<? extends BaseActivity> targetActivityClass) {
        this.startActivity(targetActivityClass, null);
    }

    protected void startActivity(String targetPath, Bundle bundle) {
        PageNavigationData pkd = new PageNavigationData(PageNavigationData.TARGET_TYPE_ACTIVITY_URL,
                targetPath, bundle);
        mPageNavigationData.setValue(pkd);
    }

    protected void startActivity(Class<? extends BaseActivity> targetActivityClass, Bundle bundle) {
        PageNavigationData pkd = new PageNavigationData(PageNavigationData
                .TARGET_TYPE_ACTIVITY_CLASS,
                targetActivityClass, bundle);
        mPageNavigationData.setValue(pkd);
    }

    protected void startExternalActivity(@NonNull Intent intent) {
        PageNavigationData pkd = new PageNavigationData(intent);
        mPageNavigationData.setValue(pkd);
    }

    //============================= getter =============================//
    public MutableLiveData<PageNavigationData> getPageNavigationData() {
        if (mPageNavigationData == null)
            mPageNavigationData = new MutableLiveData<>();
        return mPageNavigationData;
    }

    //============================= abstract method =============================//
    protected abstract void initFields();
}
