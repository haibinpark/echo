package com.sctuopuyi.echo.utils

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter


class HomeListener(var mContext: Context?) {

    var mKeyFun: KeyFun? = null
    var mHomeBtnIntentFilter: IntentFilter? = null
    var mHomeBtnReceiver: HomeBtnReceiver? = null

    init {
        mHomeBtnIntentFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        mHomeBtnReceiver = HomeBtnReceiver()
    }

    fun startListen() {
        if (mContext != null)
            mContext!!.registerReceiver(mHomeBtnReceiver, mHomeBtnIntentFilter)
        else
            LogUtil.e(TAG, "mContext is null and startListen fail")

    }

    fun stopListen() {
        if (mContext != null)
            mContext!!.unregisterReceiver(mHomeBtnReceiver)
        else
            LogUtil.e("mContext is null and stopListen fail")
    }

    fun setInterface(keyFun: KeyFun?) {
        mKeyFun = keyFun

    }

    inner class HomeBtnReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
                val reason = intent.getStringExtra("reason")
                LogUtil.d("系统的值: $reason")
                if (reason != null) {
                    if (null != mKeyFun) {
                        if (reason == "homekey") {
                            //按Home按键
                            mKeyFun!!.home()
                        } else if (reason == "recentapps") {
                            //最近任务键也就是菜单键
                            mKeyFun!!.recent()
                        } else if (reason == "assist") {
                            //常按home键盘
                            mKeyFun!!.longHome()
                        }
                    }
                }
            }
        }
    }

    interface KeyFun {
        fun home()
        fun recent()
        fun longHome()
    }

    companion object {
        val TAG = "HomeListener"
    }
}