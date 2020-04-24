package com.sctuopuyi.echo.utils

import android.content.Context
import android.os.SystemClock
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.util.*


class DateTimeUtil {

    private var context: Context? = null

    constructor(context: Context) {
        this.context = context
    }

    companion object {
        fun instance(context: Context): DateTimeUtil {
            return DateTimeUtil(context)
        }
    }


    private fun requestPermission() {
        createSuProcess("chmod 666 /dev/alarm")?.waitFor()
    }

    @Throws(IOException::class)
    private fun createSuProcess(): Process {
        val rootUser = File("/system/xbin/su")
        return if (rootUser.exists()) {
            Runtime.getRuntime().exec(rootUser.absolutePath)
        } else {
            Runtime.getRuntime().exec("su")
        }
    }

    @Throws(IOException::class)
    private fun createSuProcess(cmd: String): Process? {
        var os: DataOutputStream? = null
        val process = createSuProcess()
        try {
            os = DataOutputStream(process.outputStream)
            os.writeBytes(cmd + "\n")
            os.writeBytes("exit $?\n")
        } finally {
            if (os != null) {
                try {
                    os.close()
                } catch (e: IOException) {
                }
            }
        }
        return process
    }


    fun setDate(year: Int, month: Int, day: Int) {
//        requestPermission()
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val `when`: Long = c.timeInMillis
        if (`when` / 1000 < Int.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(`when`)
        }
        val now: Long = Calendar.getInstance().timeInMillis
        //Log.d(TAG, "set tm="+when + ", now tm="+now);
        if (now - `when` > 1000) throw IOException("failed to set Date.")
    }

    fun setTime(hour: Int, minute: Int) {
//        requestPermission()
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hour)
        c.set(Calendar.MINUTE, minute)
        val `when`: Long = c.timeInMillis
        if (`when` / 1000 < Int.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(`when`)
        }
        val now: Long = Calendar.getInstance().timeInMillis
        //Log.d(TAG, "set tm="+when + ", now tm="+now);
        if (now - `when` > 1000) throw IOException("failed to set Time.")
    }

    fun setDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
//        requestPermission()
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month - 1
        c[Calendar.DAY_OF_MONTH] = day
        c[Calendar.HOUR_OF_DAY] = hour
        c[Calendar.MINUTE] = minute
        val `when` = c.timeInMillis
        if (`when` / 1000 < Int.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(`when`)
        }
        val now = Calendar.getInstance().timeInMillis
        //Log.d(TAG, "set tm="+when + ", now tm="+now);
        if (now - `when` > 1000) throw IOException("failed to set Date.")
    }

    @Throws(IOException::class, InterruptedException::class)
    fun setDateTime(timestamp: Long) {
        requestPermission()
        if (timestamp / 1000 < Int.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(timestamp)
        }
        val now = Calendar.getInstance().timeInMillis
        //Log.d(TAG, "set tm="+when + ", now tm="+now);
        if (now - timestamp > 1000) throw IOException("failed to set Date.")
    }


    fun getDateTime(): DateTimeBean {
        val calendar = Calendar.getInstance()
        return DateTimeBean(
            calendar.get(Calendar.YEAR).toString(),
            (calendar.get(Calendar.MONTH) + 1).toString(),
            calendar.get(Calendar.DAY_OF_MONTH).toString(),
            calendar.get(Calendar.HOUR_OF_DAY).toString(),
            calendar.get(Calendar.MINUTE).toString()
        )
    }


}


data class DateTimeBean(
    val year: String,
    val month: String,
    val day: String,
    val hour: String,
    val minutes: String
)