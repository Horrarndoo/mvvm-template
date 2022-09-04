package com.example.template.support.utils;

/**
 * Created by Horrarndoo on 2019/9/17.
 * <p>
 */
public class PasswordUtils {
    /**
     * 是否合法
     *
     * @param password 密码字符串
     * @return 密码 返回0合法，返回1代表有不合法字符，返回2代表只有纯数字或纯字符
     */
    public static int isLegal(String password) {
        boolean hasChar = false;
        boolean hasNum = false;
        boolean otherNum = false;
        for (int i = 0; i < password.length(); i++) {
            int aaa = (int) password.charAt(i);
            //if password include digit
            if ('0' <= aaa && aaa <= '9') {
                hasNum = true;
            }
            //if password include a~z
            else if (('A' <= aaa && aaa <= 'Z') || ('a' <= aaa && aaa <= 'z')) {
                hasChar = true;
            } else {
                otherNum = true;
                break;
            }
        }
        if(otherNum)
            return 1;
        else if(!hasChar || !hasNum)
            return 2;
        else
            return 0;
    }
}
