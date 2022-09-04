package com.example.template.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.template.app.R;
import com.example.template.common.constants.ARouterPath;
import com.example.template.support.base.activity.BaseActivity;
import com.example.template.support.base.fragment.BaseFragment;

@Route(path = ARouterPath.MainActivity)
public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null && getTopFragment() == null) {
            BaseFragment fragment = (BaseFragment) ARouter.getInstance()
                    .build(ARouterPath.MapFragment)
                    .navigation();
            loadRootFragment(R.id.fl_container, fragment);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}