package com.sctuopuyi.echo.ui.my.domain.bean

data class CouponItemBean(
    val id: String?
    , val hasExpired: Boolean? = false
    , val amount: String?
    , val couponDesc: String?
    , val useConditionDesc: String?
    , val btnName: String?
    , val dateDesc: String?
    , val rangeDesc: String?
)