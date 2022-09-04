package com.example.template.common.constants;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 */
public interface VolumeInfo {
    String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    String ACTION_EXTRA_VOLUME_ID = "android.os.storage.extra.VOLUME_ID";
    String ACTION_EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";

    int STATE_UNMOUNTED = 0;
    int STATE_CHECKING = 1;
    int STATE_MOUNTED = 2;
    int STATE_MOUNTED_READ_ONLY = 3;
    int STATE_FORMATTING = 4;
    int STATE_EJECTING = 5;
    int STATE_UNMOUNTABLE = 6;
    int STATE_REMOVED = 7;
    int STATE_BAD_REMOVAL = 8;
}
