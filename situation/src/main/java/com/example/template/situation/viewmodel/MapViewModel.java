package com.example.template.situation.viewmodel;

import com.example.template.support.base.viewmodel.BaseViewModel;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by Horrarndoo on 2022/9/4.
 * <p>
 * 地图vm
 */
public class MapViewModel extends BaseViewModel {
    public MutableLiveData<String> text;

    @Override
    protected void initFields() {
        if (text == null)
            text = new MutableLiveData<>();
        text.setValue("MapView");
    }
}
