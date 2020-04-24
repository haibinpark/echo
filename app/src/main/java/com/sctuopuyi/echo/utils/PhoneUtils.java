package com.sctuopuyi.echo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 25/05/2017 16:51.
 */

public class PhoneUtils {


    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String hideCenterNum(String phoneStr) {
        String result = "";
        if (!TextUtils.isEmpty(phoneStr)) {
            if (phoneStr.length() > 7) {
                result = phoneStr.substring(0, 3) + "****" + phoneStr.substring(7);
            } else if (phoneStr.length() <= 3) {
                result = phoneStr;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                String three = phoneStr.substring(0, 3);
                stringBuilder.append(three);
                for (int i = 0; i < phoneStr.substring(3).length(); i++) {
                    stringBuilder.append("*");
                }
                result = stringBuilder.toString();
            }
        }
        return result;
    }

    public static String hideEmailCenterNum(String emailStr) {
        String result = "";
        if (!TextUtils.isEmpty(emailStr)) {
            if (emailStr.length() > 8) {
                result = emailStr.substring(0, 4) + "****" + emailStr.substring(8);
            } else if (emailStr.length() <= 4) {
                result = emailStr;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                String three = emailStr.substring(0, 4);
                stringBuilder.append(three);
                for (int i = 0; i < emailStr.substring(4).length(); i++) {
                    stringBuilder.append("*");
                }
                result = stringBuilder.toString();
            }
        }
        return result;
    }

    /**
     * 验证邮箱输入是否合法
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
