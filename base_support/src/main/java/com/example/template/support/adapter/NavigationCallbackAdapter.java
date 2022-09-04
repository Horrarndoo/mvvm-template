package com.example.template.support.adapter;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created by Horrarndoo on 2018/10/30.
 * <p>
 * ARouter NavigationCallback适配器，子类根据需求重写回调方法
 */
public class NavigationCallbackAdapter implements NavigationCallback {
    //找到跳转匹配路径
    @Override
    public void onFound(Postcard postcard) {

    }

    //没有匹配到跳转路径
    @Override
    public void onLost(Postcard postcard) {

    }

    //成功跳转
    @Override
    public void onArrival(Postcard postcard) {

    }

    //跳转被中断
    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
