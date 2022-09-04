/*
 * Copyright (c) 2015.  Gy.li team. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.template.support.utils;

import android.os.SystemClock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/11/21.
 */
public class SystemDateTime {
    static final String TAG = "SystemDateTime";

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setDateTime(int year, int month, int day, int hour, int minute) throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);


        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        //Log.d(TAG, "set tm="+when + ", now tm="+now);

        if(now - when > 1000)
            throw new IOException("failed to set Date.");

    }

    /**
     * @param year
     * @param month
     * @param day
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setDate(int year, int month, int day) throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        //Log.d(TAG, "set tm="+when + ", now tm="+now);

        if(now - when > 1000)
            throw new IOException("failed to set Date.");
    }

    /**
     * @param hour
     * @param minute
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setTime(int hour, int minute) throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        //Log.d(TAG, "set tm="+when + ", now tm="+now);

        if(now - when > 1000)
            throw new IOException("failed to set Time.");
    }

    /**
     * @param timeInMillis
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setTime(long timeInMillis) throws IOException, InterruptedException {
        requestPermission();
        if (timeInMillis / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(timeInMillis);
        }
        long now = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * @throws InterruptedException
     * @throws IOException
     */
    static void requestPermission() throws InterruptedException, IOException {
        //createSuProcess("chmod 666 /dev/alarm").waitFor();
    }

    /**
     * @return
     * @throws IOException
     */
    static Process createSuProcess() throws IOException {
        File rootUser = new File("/system/xbin/ru");
        if(rootUser.exists()) {
            return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
        } else {
            return Runtime.getRuntime().exec("su");
        }
    }

    /**
     * @param cmd
     * @return
     * @throws IOException
     */
    static Process createSuProcess(String cmd) throws IOException {

        DataOutputStream os = null;
        Process process = createSuProcess();

        try {
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit $?\n");
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        return process;
    }
}
