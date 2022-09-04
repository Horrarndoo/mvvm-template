package performable.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.template.common.constants.ARouterPath;
import com.example.template.situation.R;
import com.example.template.support.base.activity.BaseActivity;
import com.example.template.support.base.fragment.BaseFragment;
import com.example.template.support.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    @Override
    protected void initData() {
        super.initData();
        requestPermissions();
    }

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

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        //请求权限全部结果
        rxPermission.request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
                        //                        initCountDown();
                    }
                });
        //分别请求权限
        //        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_CALENDAR,
        //                Manifest.permission.READ_CALL_LOG,
        //                Manifest.permission.READ_CONTACTS,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.READ_SMS,
        //                Manifest.permission.RECORD_AUDIO,
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.CALL_PHONE,
        //                Manifest.permission.SEND_SMS);

        //        rxPermission.requestEach(
        //                        Manifest.permission.CAMERA,
        //                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                        Manifest.permission.READ_EXTERNAL_STORAGE,
        //                        Manifest.permission.ACCESS_FINE_LOCATION,
        //                        Manifest.permission.ACCESS_COARSE_LOCATION)
        //                .subscribe(new Consumer<Permission>() {
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        if (permission.granted) {
        //                            // 用户已经同意该权限
        //                            LogUtils.d(permission.name + " is granted.");
        //                        } else if (permission.shouldShowRequestPermissionRationale) {
        //                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        //                            LogUtils.d(permission.name + " is denied. More info should
        //                            be " +
        //                                    "provided.");
        //                        } else {
        //                            // 用户拒绝了该权限，并且选中『不再询问』
        //                            LogUtils.d(permission.name + " is denied.");
        //                        }
        //                    }
        //                });
    }
}