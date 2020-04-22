package com.sctuopuyi.echo.utils;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * Created on 22/04/2017 18:17.
 */

public class PermissionUtils {

    //获取Permission实例
    public static RxPermissions getPersionInstance(FragmentActivity activity){
        return new RxPermissions(activity);
    }
}
