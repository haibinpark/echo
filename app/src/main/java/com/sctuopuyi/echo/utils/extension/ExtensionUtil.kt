package com.sctuopuyi.echo.utils.extension

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import android.text.format.Formatter.formatFileSize
import android.app.ActivityManager


object ExtensionUtil {
    fun getLocalDns(): String? {
        var cmdProcess: Process? = null
        var reader: BufferedReader? = null
        var dnsIP = ""
        return try {
            cmdProcess = Runtime.getRuntime().exec("getprop net.dns1")
            reader = BufferedReader(InputStreamReader(cmdProcess!!.inputStream))
            dnsIP = reader!!.readLine()
            dnsIP
        } catch (e: IOException) {
            null
        } finally {
            try {
                reader!!.close()
            } catch (e: IOException) {
            }

            cmdProcess!!.destroy()
        }
    }


    fun getTotalMemory(context: Context): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return formatFileSize(context, mi.totalMem)// 将获取的内存大小规格化

    }

    fun getTranslationPeroidUnitByType(type: String?): String? {
        return when (type) {
            "1" -> "天"
            "2" -> "月"
            "3" -> "周"
            "4" -> "年"
            else -> "天"
        }
    }
}


//fun String.replaceAll(regext: String, replacement: String): String? {
//    val index = this.indexOf("//")
//    if (index > 0) {
//        var mather = this.replace(regext, replacement)
//        return this.replaceAll()
//    } else {
//        return this
//    }
//}

