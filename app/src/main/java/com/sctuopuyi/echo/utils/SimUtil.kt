package com.sctuopuyi.echo.utils

import android.app.Activity
import android.content.Context
import android.telecom.TelecomManager
import android.telephony.TelephonyManager

object SimUtil {

    /**
     * 判断是否有sim卡
     */
    fun hasSimCard(context: Context): Boolean {
        val telMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simState = telMgr.simState
        var result = true
        when (simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                result = false
            }
            TelephonyManager.SIM_STATE_UNKNOWN -> {
                result = false
            }
        }
        return result
    }


    /**
     * 判断运营商类型
     */
    fun simOperator(context: Context): SimOperator {
        val service = context.getSystemService(Activity.TELEPHONY_SERVICE)
        var teleManager: TelephonyManager? = null
        if (service != null)
            teleManager = service as TelephonyManager
        val operator = teleManager?.simOperator
        return when (operator) {
            "46000", "46002" -> SimOperator.CHINA_MOBILE
            "46001" -> SimOperator.CHINA_UNICOM
            "46003" -> SimOperator.CHINA_TELECOM
            else -> SimOperator.UNKNOWN
        }
    }


}


enum class SimOperator {
    CHINA_TELECOM, //中国典型
    CHINA_MOBILE, //中国移动
    CHINA_UNICOM, //中国联通
    UNKNOWN //未知
}