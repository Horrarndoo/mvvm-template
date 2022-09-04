package com.example.template.support.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.example.template.support.constants.Charsets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by liangfeng on 5/20/15.
 */
public class Utility {
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getMapDir() {
        if (!isExternalStorageWritable()) {
            return null;
        }

        File mapDir = null;
        try {
            mapDir = new File(Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES), "SituationMap");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the storage directory if it does not exist
        if (!mapDir.exists()) {
            if (!mapDir.mkdirs()) {
                return null;
            }
        }
        return mapDir;
    }

    public static File getMapFile(String mapName) {
        File mapFile = null;
        File mapDir = getMapDir();

        if (mapDir != null) {
            mapFile = new File(mapDir.getPath() + File.separator
                    + mapName);
            if (mapFile.exists()) {
                return mapFile;
            } else {
                return null;
            }
        }
        return mapFile;
    }

    public static File createMapFile(String mapName) {
        File mapFile = null;
        File mapDir = getMapDir();

        if (mapDir != null) {
            mapFile = new File(mapDir.getPath() + File.separator
                    + mapName);
            try {
                if (mapFile.exists()) {
                    Log.d("MINATMS", "file already existin \"createMapFile\" ,delete orignal file" +
                            ".");
                    mapFile.delete();
                }
                if (mapFile.createNewFile()) {
                    return mapFile;
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mapFile;
    }

    public static boolean isMapAppExists(Context context) {
        String packageName = "com.hfhc.soulpower.situationmap2";
        try {
            context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMapBDAppExists(Context context) {
        String packageName = "com.example.liangfeng.situationmapBaidu";
        try {
            context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMapKSAppExists(Context context) {
        String packageName = "com.example.liangfeng.situationmapKS";
        try {
            context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void writeRandomData(String filenameTemp) {
        File dir = new File(filenameTemp);
        if (!dir.exists()) {
            try {
                dir.createNewFile();
            } catch (Exception e) {
            }
        }
        byte[] tmp = new byte[1024 * 1024 * 2];
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(dir, true);
            bw = new BufferedWriter(fw);
            if (fw == null || bw == null) {
                return;
            }
            String myreadline = new String(tmp);
            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTime() {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date curdate = new Date(System.currentTimeMillis());
        return format.format(curdate);
    }

    //读取文件,返回字符串,自定义坐标系,寻北,目标等
    public static String readFileData(String FileName) {
        FileInputStream fis = null;
        String str = "";
        try {
            fis = new FileInputStream(FileName);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            str = new String(buffer, Charsets.UTF_8);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //删除文件夹下所有文件
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
