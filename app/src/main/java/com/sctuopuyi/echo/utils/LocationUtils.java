//package com.sctuopuyi.echo.utils;
//
//import com.amap.api.location.AMapLocationClientOption;
//
///**
// * 高德地图辅助工具
// * Created by \(^o^)/~ on 2016/7/7.
// */
//public class LocationUtils {
//
//    /**
//     * AMapLocationClientOption mOption = new AMapLocationClientOption();
//     * mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//     * mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//     * mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//     * mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
//     * mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//     * mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
//     * mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//     * AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
//     * mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
//     *
//     * @return
//     */
//    //单次低功耗配置
//    public static AMapLocationClientOption getLocationOption() {
//        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
//        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        aMapLocationClientOption.setHttpTimeOut(1000);
//        aMapLocationClientOption.setGpsFirst(true);
//        aMapLocationClientOption.setInterval(60000);
//        aMapLocationClientOption.setOnceLocationLatest(false);
//        aMapLocationClientOption.setOnceLocation(false);
//        aMapLocationClientOption.setNeedAddress(true);
//        aMapLocationClientOption.setWifiScan(true);
//        aMapLocationClientOption.setWifiActiveScan(true);
//        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
//        aMapLocationClientOption.setSensorEnable(true);
//        aMapLocationClientOption.setMockEnable(false);
//        return aMapLocationClientOption;
//    }
//
//}
