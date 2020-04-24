package com.sctuopuyi.echo.utils;


import com.sctuopuyi.echo.BuildConfig;

public class PlatformUtil {

    public static String BAIDU() {
        return "http://www.baidu.com";
    }

    public static String ABOUT_ME() {
        return getFront() + BEHIND_ABOUT_ME;
    }

    public static String USER_AGREEMENT() {
        return getFront() + BEHIND_USER_AGREEMENT;
    }

    public static String JOIN() {
        return getFront() + BEHIND_JOIN;
    }

    private static String getFront() {
        String result = FRONT_BETA;
        String platformFlavor = StrNumUtil.getEmptyStr(BuildConfig.FLAVOR, "beta");
        switch (platformFlavor) {
            case "beta":
                result = FRONT_BETA;
                break;
            case "pd":
                result = FRONT_PD;
                break;
        }
        return result;
    }

    private static final String BEHIND_JOIN = "joinApply";
    private static final String BEHIND_ABOUT_ME = "about";
    private static final String BEHIND_USER_AGREEMENT = "buyCarAgreement";

    private static final String FRONT_BETA = "http://192.168.0.245/hcy-h5/#/";
    private static final String FRONT_PD = "http://bkd.chejin360.com/bkd-h5/#/";
}