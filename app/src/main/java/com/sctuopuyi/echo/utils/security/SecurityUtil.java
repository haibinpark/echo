package com.sctuopuyi.echo.utils.security;

public class SecurityUtil {

    /**
     * 签名数据
     */
    public static String signData(String t) {
        return MD5Util.MD5Encode(t, "UTF-8").toUpperCase();
    }
}
