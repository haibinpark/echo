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
    }
}