package com.sctuopuyi.echo.utils;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sctuopuyi.echo.BuildConfig;


/**
 * 日志打印扩展类
 */
public class LogUtil {

    private static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = BuildConfig.APPLICATION_ID;

    static {
        if (isDebug) {
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(TAG).build()));
        } else {
            FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                    .tag(TAG)
                    .build();
            Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        }
    }

    public static void e(String tag, Object o) {
        if (isDebug) {
            Logger.e(tag, o);
        }
    }

    public static void e(Object o) {
        LogUtil.e(TAG, o);
    }

    public static void w(String tag, Object o) {
        if (isDebug) {
            Logger.w(tag, o);
        }
    }

    public static void w(Object o) {
        LogUtil.w(TAG, o);
    }

    public static void d(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    public static void prettyJson(String msg) {
        if (isDebug) {
            Logger.json(msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }
}
