package com.sctuopuyi.echo.ui.my.domain.bean

data class VisitHistoryItemBean(
    var id: String?,
    val productImg: String?
    , val productName: String?
    , val productAmount: String?
    , val productUnit: String?
    , val productLoanTime: String?
    , val productRate: String?
    , val productPeroid: String?
    , val productType: String? = "0"
    , var productUrl: String? = ""
    , val btnName: String? = ""
    , val passPer: String? = ""
    , val loanDesc: String? = ""
    , val btnUrl: String? = ""
    , val hasBtnLocal: Boolean? = true
    , val showXiaKuanLv: Boolean? = true
    , val isOrder: Boolean? = false
    , var clickType: Int? = -1
    , var isApi: Boolean? = true
    , var productStatus: String? = ""
    , var hasOff: Boolean? = false
)