package com.example.template.situation.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.template.common.constants.ARouterPath;
import com.example.template.situation.R;
import com.example.template.situation.databinding.FragmentMapBinding;
import com.example.template.situation.viewmodel.MapViewModel;
import com.example.template.support.annotation.FastClickLimit;
import com.example.template.support.base.fragment.BaseMVVMFragment;
import com.example.template.support.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Horrarndoo on 2022/9/4.
 * <p>
 * 地图页
 */
@Route(path = ARouterPath.MapFragment)
public class MapFragment extends BaseMVVMFragment<FragmentMapBinding, MapViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @NonNull
    @Override
    protected Class<MapViewModel> getViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    protected void initBindingData() {
        mBinding.setMapVM(mViewModel);
        mBinding.setHandler(new Handler());
    }

    private int clickCount = 0;

    /**
     * 处理UI事件输入，业务逻辑交由ViewModel
     */
    public class Handler {
        @FastClickLimit(interval = 100)
        public void onClick(View view) {
            if (view == mBinding.tvTest) {
                mViewModel.text.setValue(mViewModel.text.getValue() + ++clickCount);
                ToastUtils.showToast("clickCount = " + clickCount);
            }
        }

        public boolean onLongClick(View view) {
            return true;
        }
    }
}
