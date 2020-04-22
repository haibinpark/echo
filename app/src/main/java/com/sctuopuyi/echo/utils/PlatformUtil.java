package com.sctuopuyi.echo.utils;


import com.sctuopuyi.echo.BuildConfig;

public class PlatformUtil {

    /**
     * 更新请求需要的参数-编译类型
     */
    public static int getBuildTypeForRequestUpdate() {
        if (BuildConfig.DEBUG) {
//            return CheckUpdateRequest.VERSION_TYPE_TEST;
        } else {
//            return CheckUpdateRequest.VERSION_TYPE_RELEASE;
        }
        return -1;
    }

    public static String BAIDU() {
        return "http://www.baidu.com";
    }

    public static String ABOUT_ME() {
        return getFront() + BEHIND_ABOUT_ME;
    }

    public static String NEWS_LIST() {
        return getFront() + BEHIND_NEWS_LIST;
    }

    public static String USER_AGREEMENT() {
        return getFront() + BEHIND_USER_AGREEMENT;
    }

    public static String MORTGAGE_RULE() {
        return getFront() + BEHIND_MORTGAGE_RULE;
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

    private static final String BEHIND_ABOUT_ME = "userAbout";
    private static final String BEHIND_NEWS_LIST = "newsList";
    private static final String BEHIND_USER_AGREEMENT = "userAgreement";
    private static final String BEHIND_MORTGAGE_RULE = "mortgageRule";

    private static final String FRONT_BETA = "http://192.168.0.245/bkd-h5/#/";
    private static final String FRONT_PD = "http://bkd.chejin360.com/bkd-h5/#/";
}