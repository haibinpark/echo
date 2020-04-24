package com.sctuopuyi.echo.bus

class RxEvent {
    //车型
    data class EventCarModelBean(val modelId: String, val modelName: String, val guidePrice: String)

    //车颜色
    data class EventCarColorBean(
        val colorName: String,
        val colorNumber: String,
        val flag: Boolean = false
    )

    data class EventBean(val pageType: Int, val bean: Any?)


    data class LoanInfo(
        val rongziId: String?, //融资产品编号
        val loanAmount: String?, //贷款金额
        val rongziName: String?, //融资产品名称
        val zijinfangId: String? //资金方编号
    )

    //关闭
    data class EventClosePage(val pageType: Int)

    companion object {
        val EVENT_TYPE_REGISTER_OK = 3 //注册成功
        val EVENT_TYPE_LOGIN_OK = 8 //注册成功
        val EVENT_TYPE_UNLOCK_LISTEN = 15 //权限刷新
        val EVENT_TYPE_SETTING_PASSWORD_OK = 16 //输入设置密码成功
        val EVENT_PAGE_TYPE_QRSCANL = 0 //选择QR码
        val EVENT_PAGE_TYPE_SELECT_XS = 1 //选择QR码
        val EVENT_PAGE_TYPE_REFRESH_WHITELIST = 2 //选择QR码
        val EVENT_PAGE_TYPE_LOCATION = 3 //选择地址
        val EVENT_PAGE_TYPE_NETWORK_STATUS = 4 //网络类型
        val EVENT_PAGE_TYPE_NEW_LOGIN = 5 //网络类型
        val EVENT_PAGE_TYPE_DELETE = 6 //删除评论
        val EVENT_PAGE_TYPE_REPLY = 7 //回复评论
        val EVENT_PAGE_TYPE_COMMENT = 8 //回复评论
        val EVENT_PAGE_TYPE_SHOW_PICTURE = 9 //回复评论
        val EVENT_PAGE_TYPE_AI_VOL = 10 //回复评论
        val EVENT_KEY_TYPE_AI_START = 100 //AI开始键
        val EVENT_KEY_TYPE_AI_STOP = 101 //AI结束键
        val EVENT_KEY_TYPE_VP_STATUS = 200 //vp的状态
        val EVENT_KEY_TYPE_MESSAGE = 300 //vp的状态
        val EVENT_KEY_TYPE_WIFI = 400 //WIFI扫描的结果
        val EVENT_KEY_TIME_SETTING_SELECT_ITEM = 500 //WIFI扫描的结果
        val EVENT_KEY_TYPE_REGISTER_WIFI_STATUS_RECEIVER = 600 //WIFI扫描的结果
        val EVENT_KEY_TYPE_CLOSE_WLANS_PAGE = 700 //WIFI扫描的结果
        val EVENT_KEY_TYPE_PUBLISH_INCOMING = 800 //WIFI扫描的结果

    }
}