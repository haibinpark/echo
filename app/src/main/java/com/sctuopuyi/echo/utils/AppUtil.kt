package com.sctuopuyi.echo.utils

import android.content.Context
import com.sctuopuyi.echo.app.App


object AppUtil {

    @JvmStatic
    fun getAppType(): Int {
        return when (getPackageName(App.getInstance())) {
            "com.chejin360.zhxy.teacher.beta" -> 1
            "com.chejin360.zhxy.guardian.beta" -> 2
            "com.chejin360.zhxy.student.beta" -> 3
            else -> -1
        }
    }

    @JvmStatic
    fun getPackageName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            return packageInfo.packageName;
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun checkAppInstalled(context: Context, pkgName: String): Boolean {
        if (pkgName.isEmpty()) {
            return false
        }
        val packageManager = context.packageManager
        // 获取所有已安装程序的包信息
        val infos = packageManager.getInstalledPackages(0)
        if (infos == null || infos.isEmpty())
            return false
        var result = false
        infos.forEach outside@{
            if (pkgName == it.packageName) {
                result = true
                return@outside
            }
        }
        return result
    }


    fun getVersionName(context: Context): String? {
        var versionName: String? = null
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }


    fun getVersionCode(context: Context): Int? {
        var versionCode:Int? = 1
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionCode = pi.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionCode

    }


}