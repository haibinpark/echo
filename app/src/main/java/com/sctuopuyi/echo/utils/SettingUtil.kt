package com.sctuopuyi.echo.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import java.util.*


object SettingUtil {

    /**
     * 设置屏幕为手动模式
     */
    fun setScrennManualMode(context: Context) {
        val contentResolver: ContentResolver = context.contentResolver
        try {
            val mode: Int = Settings.System.getInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            )
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(
                    contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
            }
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
    }


    /**
     * 获取屏幕亮度值
     */
    fun getScreenBrightness(context: Context): Int {
        val contentResolver: ContentResolver = context.contentResolver
        val defVal = 125
        return Settings.System.getInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS, defVal
        )
    }

    /**
     * 设置屏幕的亮度
     */
    fun setScreenBrightness(context: Context, value: Int) {
        val contentResolver: ContentResolver = context.contentResolver
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS, value
        )
    }


    /**
     * 重启
     */
    fun reboot() {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("reboot"))
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun reboot(context: Context) {
        val sysService = context.getSystemService(Context.POWER_SERVICE)
        if (sysService != null) {
            val pm = sysService as PowerManager
            pm.reboot("升级")
        }
    }

    /**
     * 关机
     * @deprecated 方法不管用
     */
    fun shutdown(context: Context) {
        val intent = Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN")
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false)
        //其中false换成true,会弹出是否关机的确认窗口
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        (context as Activity).finish()
    }

    fun shutdownWithMainactivity(context: Context){
        val componetName = ComponentName("com.example.rxdshutdown",
            "com.example.rxdshutdown.MainActivity")
        val intent = Intent()
        intent.component = componetName
        context.startActivity(intent)
    }

    /**
     * 关机
     * @deprecated 方法不管用
     */
    fun shutdown() {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("reboot -p"))
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("PrivateApi")
    fun shutdownSystem() {
        try {
            val serviceManager = Class.forName("android.os.ServiceManager")
            val getService = serviceManager.getMethod("getService", String::class.java)
            val remoteService = getService.invoke(null, Context.POWER_SERVICE)
            val stub = Class.forName("android.os.IPowerManager\$Stub")
            val asInterface = stub.getMethod("asInterface", android.os.IBinder::class.java)
            val powerManager = asInterface.invoke(null, remoteService)
            val shutdownMethod = powerManager.javaClass.getMethod("shutdown", Boolean::class.java)
            shutdownMethod.invoke(powerManager, false, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 设置未知软件安装源
     */
    fun setInstallUnKnownSrc(context: Context, allow: Boolean) {
        try {
            if (allow) {
                android.provider.Settings.Global.putInt(
                    context.contentResolver,
                    android.provider.Settings.Secure.INSTALL_NON_MARKET_APPS,
                    1
                )
            } else {
                android.provider.Settings.Global.putInt(
                    context.contentResolver,
                    android.provider.Settings.Secure.INSTALL_NON_MARKET_APPS,
                    0
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 是否自动获取时区
     */
    fun isTimeZoneAuto(context: Context): Boolean {
        return try {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AUTO_TIME_ZONE
            ) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    /**
     * 设置是否自动获取时区
     */
    fun setAutoTimeZone(context: Context, auto: Boolean) {
        if (auto) {
            Settings.Global.putInt(context.contentResolver, Settings.Global.AUTO_TIME_ZONE, 1)
        } else {

            Settings.Global.putInt(context.contentResolver, Settings.Global.AUTO_TIME_ZONE, 0)
        }
    }


    /**
     * 获取默认时区
     */
    fun getDefaultTimeZone(context: Context): String? {
        return try {
            TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)
        } catch (e: Exception) {
            null
        }
    }


    /**
     * 中国时区
     */
    fun setChinaTimeZone(context: Context) {
        try {
            val sysService = context.getSystemService(Context.ALARM_SERVICE)
            if (sysService != null) {
                val alarmManager = sysService as AlarmManager
                alarmManager.setTimeZone("Asia/Shanghai")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 获取可用的TimeZone时区
     */
    fun getTimeZoneAvailableIDs(): Array<String> {
        return TimeZone.getAvailableIDs()
    }


    /**
     * 根据时间设置时区
     * 例如:中国时区 (+8)
     */
    fun setTimeZoneByTime(timeZone: String) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT$timeZone"))
    }


    /**
     * 是否自动获取时间 true 是 false 不是
     */
    fun autoGetTime(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AUTO_TIME
        ) > 0
    }


    /**
     * 设置自动获取时间
     */
    fun setAutoGetTime(context: Context, autoGetTime: Boolean) {
        val v = if (autoGetTime) 1 else 0
        Settings.Global.putInt(
            context.contentResolver,
            Settings.Global.AUTO_TIME,
            v
        )
    }


    /**
     * 设置系统格式
     * mode 12 or 24
     */
    fun set24HourMode(context: Context, hour24mode: Boolean) {
        try {
            val hourMode = if (hour24mode) {
                "24"
            } else {
                "12"
            }
            Settings.Global.putString(
                context.contentResolver,
                Settings.System.TIME_12_24, hourMode
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun is24HourMode(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.System.TIME_12_24
        ) > 0
    }

    fun restartApp(context: Context) {
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent)
    }


}