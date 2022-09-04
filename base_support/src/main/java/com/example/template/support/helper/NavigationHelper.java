package com.example.template.support.helper;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.template.base_support.R;

/**
 * Created by Horrarndoo on 2018/10/30.
 * <p>
 * 页面跳转帮助类，简化跳转流程
 */
public class NavigationHelper {
    //启动进场动画
    public static final int ANIM_START_IN = R.anim.activity_start_zoom_in;
    //启动退场动画
    public static final int ANIM_START_OUT = R.anim.activity_start_zoom_out;
    //销毁进场动画
    public static final int ANIM_FINISH_IN = R.anim.activity_finish_trans_in;
    //销毁退场动画
    public static final int ANIM_FINISH_OUT = R.anim.activity_finish_trans_out;

    private static boolean isTransAnim = true;

    /**
     * ARouter 跳转方法
     *
     * @param context     context（继承自Activity）
     * @param targetPath  要跳转的ActivityPath
     * @param bundle      携带的bundle数据
     * @param requestCode startActivityForResult 的requestCode参数
     * @param callback    跳转回调
     */
    public static void navigation(Activity context, String targetPath, Bundle bundle,
                                  int requestCode, NavigationCallback callback) {
        Postcard p = ARouter.getInstance().build(targetPath);

        if (bundle != null)
            p.with(bundle);

        if (isTransAnim)
            p.withTransition(ANIM_START_IN, ANIM_START_OUT);
        p.navigation(context, requestCode, callback);
    }

    /**
     * 是否使用overridePendingTransition过度动画
     *
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    public static boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    public static void setIsTransAnim(boolean b) {
        isTransAnim = b;
    }
}
