package com.example.template.support.utils;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.SparseIntArray;

import androidx.annotation.RawRes;

/**
 * Created by Horrarndoo on 2018/12/20.
 * <p>
 * 短音频工具类
 */
public class SoundUtils {
    private static final SoundHelper mHelper = SoundHelper.getInstance();

    private static class SoundHelper {
        private boolean isRelease;
        private SoundPool mSoundPool;
        //声音数组 key-resId value-soundId
        private SparseIntArray mSoundArray;

        private static volatile SoundHelper sHelper;

        private SoundHelper() {
            init();
        }

        private void init() {
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .build())
                    .build();
            mSoundArray = new SparseIntArray();
            isRelease = false;
        }

        public static SoundHelper getInstance() {
            if (sHelper == null) {
                synchronized (SoundHelper.class) {
                    if (sHelper == null) {
                        sHelper = new SoundHelper();
                    }
                }
            }
            return sHelper;
        }

        private void load(@RawRes int resId) {
            mSoundArray.put(resId, mSoundPool.load(AppUtils.getContext(), resId, 1));
        }

        private int mCurrentPlayingSoundId = -1;

        void play(int resId) {
            try {
                if (isRelease) {
                    init();
                }

                if (mSoundArray.get(resId, -1) == -1) {
                    load(resId);
                    mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(SoundPool soundPool, int sampleId, int
                                status) {
                            //                            LogUtils.e("sampleId = " + sampleId);
                            mCurrentPlayingSoundId = soundPool.play(sampleId, 1, 1, 1, 0, 1);
                        }
                    });
                } else {
                    //                    LogUtils.e("soundId = " + mSoundArray.get(resId));
                    mCurrentPlayingSoundId = mSoundPool.play(mSoundArray.get(resId), 1, 1, 1, 0, 1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void release() {
            if (mSoundPool != null) {
                mSoundPool.release();
                mSoundPool = null;
            }

            if (mSoundArray != null) {
                mSoundArray.clear();
                mSoundArray = null;
            }
            isRelease = true;
        }

        void stopAll() {
            try {
                if (mSoundPool != null)
                    mSoundPool.stop(mCurrentPlayingSoundId);
                release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 播放指定音频文件
     *
     * @param resId 指定音频文件资源id
     */
    public static void play(@RawRes int resId) {
        mHelper.play(resId);
    }

    /**
     * 释放音频池
     */
    public static void release() {
        mHelper.release();
    }

    /**
     * 停止播放所有音频
     */
    public static void stopAll() {
        mHelper.stopAll();
    }
}
