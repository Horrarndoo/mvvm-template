package com.example.template.common.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.example.template.common.constants.EventId;
import com.example.template.common.constants.VolumeInfo;
import com.example.template.common.event.UsbEvent;
import com.example.template.support.utils.LogUtils;
import com.example.template.support.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * USB广播接收器（拦截USB设备插入/拔出状态）
 */
public class UsbReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        String action = intent.getAction();

        if (TextUtils.isEmpty(action)) {
            return;
        }

        switch (action) {
            case VolumeInfo.ACTION_VOLUME_STATE_CHANGED://本机做host接U盘
                int usbState = intent.getIntExtra(VolumeInfo.ACTION_EXTRA_VOLUME_STATE, VolumeInfo
                        .STATE_UNMOUNTED);
                if (mOnUsbStateChangeListener != null) {
                    mOnUsbStateChangeListener.onChanged(usbState);
                }
                break;
            case "android.hardware.usb.action.USB_STATE"://本机接电脑USB
                if (intent.getExtras().getBoolean("connected")) {
                    LogUtils.d("USB连接！");
                } else {
                    LogUtils.d("USB断开！");
                    EventBus.getDefault().post(new UsbEvent(EventId.USB_STATE_DISCONNECTED));
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                LogUtils.d("USB设备连接！");
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED:
                LogUtils.d("USB设备拔出！");
                break;
            case Intent.ACTION_MEDIA_MOUNTED:
                if (intent.getData() == null) {
                    return;
                }
                String mountPath = intent.getData().getPath();
                LogUtils.d("挂载路径：" + mountPath);
                break;
            case Intent.ACTION_MEDIA_UNMOUNTED:
                break;
            default:
                break;
        }
    }

    private OnUsbStateChangeListener mOnUsbStateChangeListener;

    public void setOnUsbStateChangeListener(OnUsbStateChangeListener listener) {
        mOnUsbStateChangeListener = listener;
    }

    /**
     * USB状态变化监听
     */
    public interface OnUsbStateChangeListener {
        /**
         * 变化
         *
         * @param state usb状态
         */
        void onChanged(int state);
    }
}
