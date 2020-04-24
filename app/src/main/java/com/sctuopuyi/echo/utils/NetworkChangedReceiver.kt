package com.sctuopuyi.echo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.sctuopuyi.echo.bus.RxBus
import com.sctuopuyi.echo.bus.RxEvent
import java.lang.Exception


class NetworkChangedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var result = -1
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                LogUtil.d("API level 小于21")

                //获得ConnectivityManager对象
                val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                //获取ConnectivityManager对象对应的NetworkInfo对象
                //获取WIFI连接的信息
                val wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                //获取移动数据连接的信息
                val dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (wifiNetworkInfo.isConnected && dataNetworkInfo.isConnected) {
                    LogUtil.d("WIFI已连接,移动数据已连接")
                    result = 5
                } else if (wifiNetworkInfo.isConnected && !dataNetworkInfo.isConnected) {
                    LogUtil.d("WIFI已连接,移动数据已断开")
                    result = 4
                } else if (!wifiNetworkInfo.isConnected && dataNetworkInfo.isConnected) {
                    result = 2
                    LogUtil.d("WIFI已断开,移动数据已连接")
                } else {
                    LogUtil.d("WIFI已断开,移动数据已断开")
                    result = 0
                }
            } else {
                LogUtil.d("API level 大于21")

                //获得ConnectivityManager对象
                val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                //获取所有当前已有连接上状态的网络连接的信息
                val networks = connMgr.allNetworks

                //用于记录最后的网络连接信息
                result = 0//mobile false = 1, mobile true = 2, wifi = 4

                //通过循环将网络信息逐个取出来
                for (i in networks.indices) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    val networkInfo = connMgr.getNetworkInfo(networks[i])

                    //检测到有数据连接，但是并连接状态未生效，此种状态为wifi和数据同时已连接，以wifi连接优先
                    if (networkInfo.type == ConnectivityManager.TYPE_MOBILE && !networkInfo.isConnected) {
                        result += 1
                    }

                    //检测到有数据连接，并连接状态已生效，此种状态为只有数据连接，wifi并未连接上
                    if (networkInfo.type == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected) {
                        result += 2
                    }

                    //检测到有wifi连接，连接状态必为true
                    if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                        result += 4
                    }
                }

                //因为存在上述情况的组合情况，以组合相加的唯一值作为最终状态的判断
//            when (result) {
//                0 -> {
//                    LogUtil.d("WIFI已断开,移动数据已断开")
//                }
//                2 -> {
//                    LogUtil.d("WIFI已断开,移动数据已连接")
//                }
//                4 -> {
//                    LogUtil.d("WIFI已连接,移动数据已断开")
//                }
//                5 -> {
//                    LogUtil.d("WIFI已连接,移动数据已连接")
//                }
//            }
//            RxBus.publish(RxEvent.EventBean(RxEvent.EVENT_PAGE_TYPE_NETWORK_STATUS, result))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        RxBus.publish(RxEvent.EventBean(RxEvent.EVENT_PAGE_TYPE_NETWORK_STATUS, result))
    }
}