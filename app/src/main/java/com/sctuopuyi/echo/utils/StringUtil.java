package com.sctuopuyi.echo.utils;

import android.text.TextUtils;

import com.sctuopuyi.echo.app.App;

import java.util.Locale;


/**
 * Created by 大灯泡 on 2016/10/28.
 * <p>
 * 字符串工具类
 */

public class StringUtil {

    public static boolean noEmpty(String originStr) {
        return !TextUtils.isEmpty(originStr);
    }


    public static boolean noEmpty(String... originStr) {
        boolean noEmpty = true;
        for (String s : originStr) {
            if (TextUtils.isEmpty(s)) {
                noEmpty = false;
                break;
            }
        }
        return noEmpty;
    }

    /**
     * 从资源文件拿到文字
     */
    public static String getResourceString(int strId) {
        String result = "";
        if (strId > 0) {
            result = App.Companion.getInstance().getResources().getString(strId);
        }
        return result;
    }

    /**
     * 从资源文件得到文字并format
     */
    public static String getResourceStringAndFormat(int strId, Object... objs) {
        String result = "";
        if (strId > 0) {
            result = String.format(Locale.getDefault(), App.Companion.getInstance().getResources().getString(strId), objs);
        }
        return result;
    }
}
